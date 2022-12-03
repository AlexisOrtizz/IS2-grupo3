package com.proyecto.is2.proyecto.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralUtils {
    // Admin Default config
    public final static String ADMIN_USER = "admin";
    public final static String ADMIN_EMAIL = "admin@gmail.com";
    public final static String ADMIN_PASS = "12345678";
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String USER_CODE ="usuario";
    public final static String PROJECT_CODE ="proyecto";
    public final static String BACKLOG_CODE ="backlog";
    public final static String SPRINT_CODE ="sprint";
    public final static String STORY_CODE = "user-story";
    public final static String ROL_CODE = "rol";
    public final static String PERMISO_CODE = "permiso";
    public final static String VIEW_CODE = "vista";
    public final static Long EMPTY_VALUE = -1L;
    public final static String FINALIZADO_VALUE = "Finalizado";
    public final static String CANCELADO_VALUE = "Cancelado";
    public final static String PENDIENTE_VALUE = "Pendiente";
    public final static String ACTIVO_VALUE = "Activo";
    public final static String DONE = "Done";
    public final static String TO_DO = "To do";
    public final static String DOING = "Doing";

    /* Estados posibles para un proyecto */
    public static List<String> getEstadosProyecto() {
        return Arrays.asList(PENDIENTE_VALUE, ACTIVO_VALUE, CANCELADO_VALUE, FINALIZADO_VALUE);
    }

    /* Prioridades posibles para un User Story */
    public static List<String> getStoryPrioridades() {
        return Arrays.asList("Baja", "Media", "Alta", "Muy alta");
    }

    /* Estados de un User story*/
    public static List<String> getEstadosUserStory() {
        return Arrays.asList(TO_DO, DOING, DONE);
    }

    public static String getEstadoEliminado() {
        return "Eliminado";
    }

    /* Estados posibles para un sprint */
    public static List<String> getEstadosSprint() {
        return Arrays.asList("Pendiente","Activo", "Cancelado", FINALIZADO_VALUE);
    }

    /* Convertir string a fecha */
    public static String getStringToDate(LocalDate date) {
        try {
            return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            System.out.println("No se pudo convertir " + date + " a String: ");
            return "";
        }
    }

    /* Convertir fecha a string */
    public static LocalDate getDateToString(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            return null;
        }
    }
}
