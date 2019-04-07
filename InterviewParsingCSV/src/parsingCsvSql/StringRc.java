package parsingCsvSql;

public class StringRc {
    public final String tableRec(String[] str) {
        int i = 0;
        String strRec = "";
        // from s[0] to s[8] 
        while (i<7) {
            if (str[i].startsWith("\"")) {
            	strRec = strRec + String.format("%s,", str[i]);
            } else {
            	strRec = strRec + String.format("\"%s\",", str[i]);
            }
            i++;
        }
        if (str[7].toLowerCase().equals("true")) {
            strRec = strRec + "1,";
        } else {
        	strRec = strRec + "0,";
        }
        if (str[8].toLowerCase().equals("true")) {
        	strRec = strRec + "1,";
        } else {
        	strRec = strRec + "0,";
        }
        // wrap last element in double quotes if needed
        if (str[9].startsWith("\"")) {
        	strRec = strRec + String.format("%s", str[9]);
        } else {
        	strRec = strRec + String.format("\"%s\"", str[9]);
        }
        return strRec;
    }

}
