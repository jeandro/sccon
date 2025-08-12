package br.com.jeandro.sccon.util;

import java.time.*;

public final class DateUtils {
    private DateUtils() {}

    public static long idadeEmDias(LocalDate nascimento, LocalDate hoje) {
        return Duration.between(nascimento.atStartOfDay(), hoje.atStartOfDay()).toDays();
    }
    public static long idadeEmMeses(LocalDate nascimento, LocalDate hoje) {
        Period p = Period.between(nascimento, hoje);
        return p.toTotalMonths();
    }
    public static double idadeEmAnos(LocalDate nascimento, LocalDate hoje) {
        Period p = Period.between(nascimento, hoje);
        return p.getYears() + p.getMonths()/12.0 + p.getDays()/365.2425;
    }
}
