package org.aist.aide.formexpert.domain.factories;

import org.aist.aide.formexpert.domain.models.Form;
import org.aist.aide.formexpert.domain.models.pipe.filters.Filter;
import org.aist.aide.formexpert.service.api.http.GeneralTypeTemplate;
import org.aist.aide.formexpert.service.api.http.LabelMultiplexerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ClassifiersFactory {
    private static final Logger LOGGER = Logger.getLogger(ClassifiersFactory.class.getName());

    private LabelMultiplexerTemplate multiplexerTemplate;
    private GeneralTypeTemplate generalTypeTemplate;

    public ClassifiersFactory(@Autowired LabelMultiplexerTemplate multiplexerTemplate, @Autowired GeneralTypeTemplate generalTypeTemplate) {
        this.multiplexerTemplate = multiplexerTemplate;
        this.generalTypeTemplate = generalTypeTemplate;
    }

    public Filter<Form> createLabelMultiplexFilter() {
        return new Filter<>(form -> {
           for(var field: form.getFields()) {
               try {
                   var label = multiplexerTemplate.multiplexLabel(field.getLabel());
                   field.setmLabel(label);
               } catch (Exception e) {
                   LOGGER.severe(e.getStackTrace().toString());
                   return null;
               }
           }
           return form;
        });
    }

    public Filter<Form> createTypeFilter() {
        return new Filter<>(form -> {
           for(var field: form.getFields()) {
               try {
                    field.setType(generalTypeTemplate.getType(field.getValue()));
               } catch (Exception e) {
                   LOGGER.severe(e.getStackTrace().toString());
                   return null;
               }
           }
           return form;
        });
    }
}