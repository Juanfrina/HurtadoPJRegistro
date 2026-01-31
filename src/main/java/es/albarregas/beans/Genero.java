package es.albarregas.beans;

/**
 * Enum que representa el género del usuario.
 * @author jfco1
 */
public enum Genero {
    Mujer("M"),
    Hombre("H"),
    Otro("O");

    private final String inicial;

    Genero(String inicial) {
        this.inicial = inicial;
    }

    public String getInicial() {
        return inicial;
    }

    /**
     * Obtiene el Genero a partir de su inicial (M, H, O).
     * @param inicial La inicial del género
     * @return El enum Genero correspondiente
     */
    public static Genero fromInicial(String inicial) {
        for (Genero g : Genero.values()) {
            if (g.getInicial().equalsIgnoreCase(inicial)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Inicial de género no válida: " + inicial);
    }

    /**
     * Obtiene el Genero a partir de su nombre (Mujer, Hombre, Otro).
     * @param nombre El nombre del género
     * @return El enum Genero correspondiente, o null si no coincide
     */
    public static Genero fromNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        for (Genero g : Genero.values()) {
            if (g.name().equalsIgnoreCase(nombre.trim())) {
                return g;
            }
        }
        return null;
    }
}
