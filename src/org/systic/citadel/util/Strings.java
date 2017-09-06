package org.systic.citadel.util;

public class Strings {

    public static String upperCamelCase(String string) {
        if (!string.contains(" ")) {
            if (string.length() <= 1) {
                return string.toUpperCase();
            } else {
                return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
            }
        }

        String ret = "";

        for (String str : string.split(" ")) {
            if (str.length() <= 1) {
                ret += str.toUpperCase();
            } else {
                ret += str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + " ";
            }
        }

        return ret.trim();
    }

    public static boolean startsWithVowel(String string){
        String str = string.toLowerCase();

        return str.startsWith("a") || str.startsWith("e") || str.startsWith("i") || str.startsWith("o") || str.startsWith("u");
    }

    public static String concat(String[] array, int start, int end){
        String current = "";
        for(int i = start; i < (end > array.length ? array.length : end); i++){
            current += array[i] + " ";
        }
        return current.trim();
    }

    public static String concat(String[] args, String splitter){
        String current = "";
        for(String s : args){
            current += s + splitter;
        }

        if(current.endsWith(splitter)) current = current.substring(0, current.length()-splitter.length());

        return current.trim();
    }

    public static String concat(String[] args, char splitter){
        return concat(args, splitter + " ");
    }

    public static String concat(String[] args){
        return concat(args, " ");
    }
    
    public static String limit(int length, String string){
        return (string.length() <= length ? string : string.substring(0, length));
    }

}
