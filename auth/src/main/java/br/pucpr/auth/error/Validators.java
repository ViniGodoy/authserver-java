package br.pucpr.auth.error;

public class Validators {
    public static String sortDir(String sortDir) {
        if (sortDir == null) return "ASC";

        sortDir = sortDir.toUpperCase();
        if (!sortDir.equals("ASC") && !sortDir.equals("DESC")) {
            throw new BadRequestException("sortDir inválido: " + sortDir);
        }
        return sortDir;
    }
}
