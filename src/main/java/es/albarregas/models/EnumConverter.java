package es.albarregas.models;

import es.albarregas.beans.Usuario.Genero;
import org.apache.commons.beanutils.Converter;

/**
 * Convertidor para el enum Genero.
 * Convierte valores String del formulario (Mujer, Hombre, Otro) al enum Genero.
 * Se usa con BeanUtils.populate()
 * @author jfco1
 */
public class EnumConverter implements Converter {

    /**
     * Convierte un valor String al enum Genero.
     * Acepta tanto el nombre completo (Mujer, Hombre, Otro) como la inicial (M, H, O).
     *
     * @param type La clase de destino (Genero.class)
     * @param value El valor a convertir (String)
     * @return El valor convertido a Genero, o null si está vacío
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        // Si el valor es null o está vacío, devuelvo null
        if (value == null) {
            return null;
        }
        
        String strValue = value.toString().trim();
        if (strValue.isEmpty()) {
            return null;
        }
        
        // Si es una inicial (M, H, O), convertir usando fromInicial
        if (strValue.length() == 1) {
            return (T) Genero.fromInicial(strValue);
        }
        
        // Si es el nombre completo (Mujer, Hombre, Otro), usar fromNombre
        Genero genero = Genero.fromNombre(strValue);
        if (genero != null) {
            return (T) genero;
        }
        
        // Si no se pudo convertir, intentar valueOf
        try {
            return (T) Genero.valueOf(strValue);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
