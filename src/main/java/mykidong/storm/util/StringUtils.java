package mykidong.storm.util;


public class StringUtils {

    /**
     * 생성자를 private으로 지정하여 new 키워드로의 instance 생성을 방지한다.
     */
    private StringUtils() {
    }

    /**
     * 첫번째 파라미터 값이 NULL인지  체크 한 후
     * NULL 이라면 두번째 인자로 반환하고 아니라면 첫번째 인자를 반환한다.
     *
     * <p>
     * @param String str
     * @param String defaultValue
     * @return String
     */
    public static String getString(String str, String defaultValue) {
        return (str == null || str.equals("")) ? defaultValue : str.trim();
        //return NVL(str, defaultValue);
    }

    /**
     * 첫번째 파라미터 값이 NULL인지  체크 한 후
     * NULL 이라면 빈 값을  반환하고 아니라면 첫번째 인자를 반환한다.
     *
     * <p>
     * @param String str
     * @return String
     */
    public static String getString(String str) {
        return NVL(str, "");
    }

    /**
     * 문자열의 교체 (legacy)
     *
     * @param str
     *            문자열
     * @param oldsub
     *            변경시킬 문자열
     * @param newsub
     *            변경할 문자열
     * @return String 변경된 문자열
     * @see java.lang.String#replaceAll(String, String)
     */
    public static String replace(String str, String oldsub, String newsub) {
        if ((str == null) || (oldsub == null) || (newsub == null)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int length = oldsub.length();
        int x = 0;
        int y = str.indexOf(oldsub);

        while (x <= y) {
            sb.append(str.substring(x, y));
            sb.append(newsub);
            x = y + length;
            y = str.indexOf(oldsub, x);
        }

        sb.append(str.substring(x));

        return sb.toString();
    }

    /**
     * @param str
     *            문자열
     * @return String 문자열이 null일 경우 "", 그외에는 문자열 그대로
     */
    public static String NVL(String str) {
        return NVL(str, "");
    }

    /**
     * @param str
     *            문자열
     * @param rep
     *            문자열이 null일 경우 대체 문자열
     * @return String 문자열이 null일 경우 대체 문자열, 그외에는 문자열 그대로
     */
    public static String NVL(String str, String rep) {
        return str == null ? rep : str;
    }

    /**
     * byte[]를 String으로 변환
     *
     * <p>
     *
     * @param byte[]
     *            data
     * @return String
     */
    public static String byteToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            // buf.append(byteToHex(data[i]).toUpperCase());
            buf.append(byteToHex(data[i]));
        }
        return buf.toString();
    }

    /**
     * byte를 String으로 변환
     *
     * <p>
     *
     * @param byte
     *            data
     * @return String
     */
    public static String byteToHex(byte data) {
        StringBuffer buf = new StringBuffer();
        buf.append(hexToChar((data >>> 4) & 0x0F));
        buf.append(hexToChar(data & 0x0F));
        return buf.toString();
    }

    /**
     * 헥사값을 char로 변환
     *
     * <p>
     *
     * @param int
     *            i
     * @return char
     */
    public static char hexToChar(int i) {
        if ((i >= 0) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }

    /**
     * <pre>
     *
     * desc : String을 int로 반환 기본값은 0
     *
     * </pre>
     * @param str
     * @return int
     */
    public static int getInt(String str) {
        return getInt(str, 0);
    }

    /**
     * <pre>
     *
     * desc : String을 int로 반환, 에러 발생시 두번째 값 반환
     *
     * </pre>
     * @param str value
     * @param default_int 기본값
     * @return int
     */
    public static int getInt(String str, int default_int) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return default_int;
        }
    }

    /**
     * <pre>
     *
     * desc : String을 boolean으로 반환
     *
     * </pre>
     * @param str value
     * @param defaultValue 기본값
     * @return boolean
     */
    public static boolean getBoolean(String str, boolean defaultValue) {
        try {
            return (str == null || str.equals("")) ? defaultValue : (Boolean.valueOf(str)).booleanValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * <pre>
     *
     * desc : String을 boolean으로 반환
     *
     * </pre>
     * @param str value
     * @return boolean
     */
    public static boolean getBoolean(String str) {
        boolean defaultValue = false;
        return getBoolean(str, defaultValue);
    }

    /**
     * <pre>
     *
     * desc : String을 long으로 반환
     *
     * </pre>
     * @param str
     * @return
     */
    public static long getLong(String str) {
        return getLong(str, 0l);
    }

    /**
     * <pre>
     *
     * desc : String을 long으로 반환
     *
     * </pre>
     * @param str
     * @param default_long
     * @return
     */
    public static long getLong(String str, long default_long) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return default_long;
        }
    }

    /**
     * <pre>
     *
     * desc : String을 double로 반환
     *
     * </pre>
     * @param str
     * @return
     */
    public static double getDouble(String str) {
        return getDouble(str, 0d);
    }

    /**
     * <pre>
     *
     * desc : String을 double로 반환
     *
     * </pre>
     * @param str
     * @param default_double
     * @return
     */
    public static double getDouble(String str, double default_double) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return default_double;
        }
    }

    /**
     * 의미있는 String인지 여부 판단
     * null과 empty일때 false
     *
     * @param s
     * @return
     */
    public static boolean isSemantic(String s) {
        if(s == null) {
            return false;
        }
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * null 또는 "" 일 경우 true
     * @param string
     * @return true
     */
    public static boolean isEmpty(String string) {
        return !isSemantic(string);
    }

}
