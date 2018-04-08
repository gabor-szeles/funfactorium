package org.funfactorium;

import org.funfactorium.funfacts.FunFactNotFoundException;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class CustomExceptionArgumentConverter implements ArgumentConverter {


    @Override
    public Object convert(
            Object input, ParameterContext parameterContext)
            throws ArgumentConversionException {
        if (input instanceof RuntimeException) {
            return input;
        }

        if (input instanceof String) {
            if(input.equals("topic")) {
                return new TopicNotFoundException();
            } else if(input.equals("funfact")) {
                return new FunFactNotFoundException();
            } else if(input.equals("null")) {
                return new NullPointerException();
            }
        }

        throw new ArgumentConversionException(input + " is no valid exception!");
    }
}
