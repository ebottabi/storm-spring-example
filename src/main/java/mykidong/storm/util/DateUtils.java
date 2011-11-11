package mykidong.storm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

    private DateUtils(){}

    /**
     *  참고할 format letter
     * Letter  Date or Time Component  Presentation  Examples
     *   G     Era designator  Text  AD
     *   y     Year  Year  1996; 96
     *   M     Month in year  Month  July; Jul; 07
     *   w     Week in year  Number  27
     *   W     Week in month  Number  2
     *   D     Day in year  Number  189
     *   d     Day in month  Number  10
     *   F     Day of week in month  Number  2
     *   E     Day in week  Text  Tuesday; Tue
     *   a     Am/pm marker  Text  PM
     *   H     Hour in day (0-23)  Number  0
     *   k     Hour in day (1-24)  Number  24
     *   K     Hour in am/pm (0-11)  Number  0
     *   h     Hour in am/pm (1-12)  Number  12
     *   m     Minute in hour  Number  30
     *   s     Second in minute  Number  55
     *   S     Millisecond  Number  978
     *   z     Time zone  General time zone  Pacific Standard Time; PST; GMT-08:00
     *   Z     Time zone  RFC 822 time zone  -0800
     */

    private static String SIMPLEDATEFORMAT = "yyyyMMdd";

    /**
     * @param format
     *            날짜형식
     * @return format으로 설정된 SimpleDateFormat 인스턴스
     */
    public static SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format, java.util.Locale.KOREA);
    }

    /**
     * @param strDate 문자열타입의 날짜
     * @return 해당 문자열에 맞는 SimpleDateFormat
     */
    private static SimpleDateFormat getLocalFormat(String strDate) {
        if (strDate != null && strDate.length() >= 8) {
            if (strDate.indexOf("-") > 0)
                return getSimpleDateFormat("yyyy-MM-dd");
            else if (strDate.indexOf("/") > 0)
                return getSimpleDateFormat("yyyy/MM/dd");
            else if (strDate.indexOf(".") > 0)
                return getSimpleDateFormat("yyyy.MM.dd");
            else
                return getSimpleDateFormat(SIMPLEDATEFORMAT);
        } else {
            return getSimpleDateFormat(SIMPLEDATEFORMAT);
        }
    }

    /**
     * <pre>
     *
     * desc : 날짜형태의 String을 "yyyyMMdd" 날짜 포맷으로 반환한다.
     *
     * </pre>
     * @param str
     * @return
     */
    public static String format(String str) {
        return format(str, SIMPLEDATEFORMAT);
    }

    /**
     * <pre>
     *
     * desc : 날짜형태의 String을  두번째 인자의 날짜 포맷으로 반환한다.
     *
     * </pre>
     * @param str
     * @param fmt
     * @return
     */
    public static String format(String str, String fmt) {
        if(StringUtils.isEmpty(str)) return "";
        return format(parse(str), fmt);
    }

    /**
     * <pre>
     *
     * desc : 첫번째 인자의 String을 두번째 인자의 포맷으로 날짜타입으로 변환 후 세번째 포맷의 String으로 반환한다.
     * DateUtils.format("2010-09-01", "yyyy-MM-dd", "yyyyMMdd")
     * 결과 "20100901"
     *
     * </pre>
     * @param str
     * @param fmt
     * @param resultfmt
     * @return
     */
    public static String format(String str, String fmt, String resultfmt) {
        if(StringUtils.isEmpty(str)) return "";
        return format(parse(str, fmt), resultfmt);
    }

    /**
     * Date를 format 형태의 String으로 반환한다.
     * @param date 시간
     * @param format 날짜형식
     * @return format으로 설정된 날짜 String
     * @see java.text.SimpleDateFormat
     */
    public static String format(Date date, String format) {
        if(date == null) return "";
        String dateString;
        if (format == null) {
            dateString = getSimpleDateFormat(SIMPLEDATEFORMAT).format(date);
        } else {
            dateString = getSimpleDateFormat(format).format(date);
        }
        return dateString;
    }

    /**
     * String을 Date로 변환하기
     * 첫번째 인자와 두번째 형태가 일치해야 한다.
     *
     * @param String s //원하는 날짜 "2006.03.04"
     * @param String s1 //포맷. "yyyy.MM.dd"
     * @return Date
     */
    public static Date parse(String s, String s1) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
        try {
            return simpledateformat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            //System.out.println("에러가 발생했습니다. the value is "+s+", the format is "+ s1);
        }
        return null;
    }

    /**
     * <pre>
     *
     * String을 Date로 변환하기
     * 전달받은 인자의 포맷은 "yyyyMMdd" 형태이어야 한다.
     * </pre>
     * @param s
     * @return
     */
    public static Date parse(String s) {
        String s1 = "yyyyMMdd";
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
        try {
            return simpledateformat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance(java.util.Locale.KOREA);
    }

    /**
     * 입력된 일자를 Calendar 객체로 반환한다.
     *
     * @param year 년
     * @param month  월
     * @param date 일
     * @return 해당일자에 해당하는 Calendar
     */
    public static Calendar getCalendar(int year, int month, int date) {
        return getCalendar(year, month, date, 0, 0, 0);
    }

    /**
     * 입력된 정보를 Calendar 객체로 반환한다.
     *
     * @param year 년
     * @param month 월
     * @param date 일
     * @param hour 시
     * @param minute 분
     * @return 해당일자에 해당하는 Calendar
     */
    public static Calendar getCalendar(int year, int month, int date, int hour, int minute) {
        return getCalendar(year, month, date, hour, minute, 0);
    }

    /**
     * 입력된 정보를 Calendar 객체로 반환한다.
     *
     * @param year 년
     * @param month 월
     * @param date 일
     * @param hour 시
     * @param minute 분
     * @param second 초
     * @return 해당일자에 해당하는 Calendar
     */
    public static Calendar getCalendar(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = getCalendar();
        calendar.set(year, month - 1, date, hour, minute, second);
        return calendar;
    }

    /**
     * 입력된 일자를 Calendar 객체로 반환한다.
     *
     * @param argDate
     *            변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
     * @return 해당일자에 해당하는 Calendar
     */
    public static Calendar toCalendar(String pDate) {
        String date = toYYYYMMDDDate(pDate);

        return getCalendar(Integer.parseInt(date.substring(0, 4)), Integer
                .parseInt(date.substring(4, 6)), Integer.parseInt(date
                .substring(6, 8)));
    }

    /**
     * 현재 시간을 format 형태로 반환한다.
     *
     * @param format 날짜형식
     * @return format으로 설정된 날짜 String
     * @see java.text.SimpleDateFormat
     */
    public static String getToday(String format) {
        return format(new Date(), format);
    }


    /**
     * 현재 시간을 "yyyyMMdd" 형태로 반환한다.
     *
     * @return format으로 설정된 날짜 String
     * @see java.text.SimpleDateFormat
     */
    public static String getToday() {
        return getSimpleDateFormat(SIMPLEDATEFORMAT).format(getDate());	// 기본값
    }

    /**
     * 현재 일자를 Date 객체로 반환한다.
     *
     * @return 현재 일자에 해당하는 Date
     */
    public static Date getDate() {
        return getCalendar().getTime();
    }


    /**
     * 입력된 일자를 Date 객체로 반환한다.
     * @param year 년
     * @param month 월
     * @param date 일
     * @return 해당일자에 해당하는 Date
     */
    public static Date getDate(int year, int month, int date) {
        return getCalendar(year, month, date).getTime();
    }

    /**
     * 입력된 정보를 Date 객체로 반환한다.
     *
     * @param year 년
     * @param month 월
     * @param date 일
     * @param hour 시
     * @param minute 분
     * @return 해당일자에 해당하는 Date
     */
    public static Date getDate(int year, int month, int date, int hour, int minute) {
        return getCalendar(year, month, date, hour, minute).getTime();
    }

    /**
     * 입력된 정보를 Date 객체로 반환한다.
     *
     * @param year 년
     * @param month 월
     * @param date 일
     * @param hour 시
     * @param minute 분
     * @param second  초
     * @return 해당일자에 해당하는 Date
     */
    public static Date getDate(int year, int month, int date, int hour, int minute, int second) {
        return getCalendar(year, month, date, hour, minute, second).getTime();
    }

    /**
     * 입력된 일자를 Date 객체로 반환한다.
     *
     * @param argDate
     *            변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
     * @return 해당일자에 해당하는 Calendar
     */
    public static Date toDate(String pDate) {
        return parse(toYYYYMMDDDate(pDate));
    }

    /**
     * 현재 일자를 Timestamp 객체로 반환한다.
     *
     * @return 현재 일자에 해당하는 Timestamp
     */
    public static Timestamp getTimeStamp() {

        return new Timestamp(getCalendar().getTimeInMillis());
    }


    /**
     * 입력된 일자를 Timestamp 객체로 반환한다.
     *
     * @param year 년
     * @param month 월
     * @param date 일
     * @return 해당일자에 해당하는 Timestamp
     */
    public static Timestamp getTimeStamp(int year, int month, int date) {
        return new Timestamp(getCalendar(year, month, date).getTimeInMillis());
    }

    /**
     * 입력된 정보를 Timestamp 객체로 반환한다.
     *
     * @param year 년
     * @param month 월
     * @param date 일
     * @param hour 시
     * @param minute 분
     * @return 해당일자에 해당하는 Timestamp
     */
    public static Timestamp getTimeStamp(int year, int month, int date, int hour,int minute) {
        return new Timestamp(getCalendar(year, month, date, hour, minute).getTimeInMillis());
    }

    /**
     * 입력된 정보를 Timestamp 객체로 반환한다.
     * @param year 년
     * @param month 월
     * @param date 일
     * @param hour 시
     * @param minute 분
     * @param second 초
     * @return 해당일자에 해당하는 Timestamp
     */
    public static Timestamp getTimeStamp(int year, int month, int date, int hour,
            int minute, int second) {
        return new Timestamp(getCalendar(year, month, date, hour, minute, second).getTimeInMillis());
    }

    /**
     * <pre>
     *
     * desc : "yyyyMMdd" 형태의 날짜를 Timestamp 타입으로 반환한다.
     *
     * </pre>
     * @param yyyymmdd
     * @return
     */
    public static Timestamp getTimeStamp(String yyyymmdd){
        if(yyyymmdd == null) return null;
        Date dt = parse(yyyymmdd);
        return Timestamp.valueOf(getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt));
    }

    /**
     * <pre>
     *
     * desc : 전달받은 String 형태의 날짜를 두번째 인자의 포맷으로 날짜로 변환해서 Timestamp 타입으로 반환한다.
     *
     * </pre>
     * @param yyyymmdd
     * @param fmt
     * @return
     */
    public static Timestamp getTimeStamp(String yyyymmdd, String fmt){
        if(yyyymmdd == null) return null;
        Date dt = parse(yyyymmdd, fmt);
        return Timestamp.valueOf(getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt));
    }

    /**
     * 어떤 날자가( 1998.01.02, 98.01.02, 19980102, 980102 ) 들어오건 간에 YYYYMMDD로 변환한다.
     *
     * @param argDate
     *            변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
     * @return 변환된 일자 또는 잘못된 일자의 경우 Blank String을 반환
     */
    public static String toYYYYMMDDDate(String argDate) {
        boolean isMunja = false;
        boolean isCorrectArg = true;
        String subArg = "";
        String date = "";
        String result = "";

        if (argDate != null)
            subArg = argDate.trim();

        for (int inx = 0; inx < subArg.length(); inx++) {
            if (java.lang.Character.isLetter(subArg.charAt(inx))
                    || subArg.charAt(inx) == ' ') {
                isCorrectArg = false;
                break;
            }
        }

        if (!isCorrectArg) {
            return "";
        }

        if (subArg.length() != 8) {
            if (subArg.length() != 6 && subArg.length() != 10) {
                return "";
            }

            if (subArg.length() == 6) {
                if (Integer.parseInt(subArg.substring(0, 2)) > 50)
                    date = "19";
                else
                    date = "20";

                result = date + subArg;
            }

            if (subArg.length() == 10)
                result = subArg.substring(0, 4) + subArg.substring(5, 7)
                        + subArg.substring(8, 10);
        } else {// 8자린 경우 ( 98.01.02, 19980102 )

            try {
                Integer.parseInt(subArg);
            } catch (NumberFormatException ne) {
                isMunja = true;
            }

            if (isMunja) // 98.01.02 혹은 98/01/02 형식의 포맷일 경우
            {
                date = subArg.substring(0, 2) + subArg.substring(3, 5)
                        + subArg.substring(6, 8);
                if (Integer.parseInt(subArg.substring(0, 2)) > 50)
                    result = "19" + date;
                else
                    result = "20" + date;
            } else
                // 19980102 형식의 포맷인 경우
                return subArg;
        }
        return result;
    }


    /**
     * pDate에서 pMonths만큼 다음달의 날자를 구한다.
     *
     * @param pDate
     *            기준일
     * @param pMonths
     *            개월수
     * @return 입력된 날자에서 pMonth 이후 달의 날자
     */
    public static String getAddMonth(String pDate, String formatStr, int pMonths) {
        SimpleDateFormat localFormat = new SimpleDateFormat(formatStr, java.util.Locale.KOREA);
        java.util.Calendar cal = toCalendar(pDate);
        cal.add(Calendar.MONTH, pMonths);
        return localFormat.format(cal.getTime());
    }

    /**
     * 두 날짜 사이 차이를 반환한다.
     *
     * @param Date
     *            date1 //과거
     * @param Date
     *            date2 //미래
     * @return int
     */
    public static int dateDiff(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * pDate 부터 pDays 만큼의 날자를 구한다. pDays에 음수(-) 값은 지원하지 않는다.
     *
     * @param pDate
     *            기준 날짜
     * @param pDays
     *            더해질 일자
     * @return 변환된날짜
     */
    public static String getNextDate(String pDate, int pDays) {

        if (pDays < 0) {
            return pDate;
        }

        SimpleDateFormat localFormat = getLocalFormat(pDate);
        java.util.Calendar cal = toCalendar(pDate);
        cal.add(Calendar.DATE, pDays);
        return localFormat.format(cal.getTime());
    }

    /**
     * pDate에서 pMonths만큼 다음달의 날자를 구한다.
     *
     * @param pDate
     *            기준일
     * @param pMonths
     *            개월수
     * @return 입력된 날자에서 pMonth 이후 달의 날자
     */
    public static String getNextMonthDate(String pDate, int pMonths) {
        if (pMonths < 0) {
            return pDate;
        }
        SimpleDateFormat localFormat = getLocalFormat(pDate);
        java.util.Calendar cal = toCalendar(pDate);

        cal.add(Calendar.MONTH, pMonths);

        return localFormat.format(cal.getTime());
    }

    /**
     * pDate(YYYYMMDD)에서 pDays일자 만큼 전의 날짜를 구해준다.
     *
     * @param pDate
     *            기준일
     * @param pDays
     *            몇일전
     * @return 수정된 일자
     */
    public static String getPrevDate(String pDate, int pDays) {
        if (pDays < 0) {
            return pDate;
        }
        SimpleDateFormat localFormat = getLocalFormat(pDate);
        java.util.Calendar cal = toCalendar(pDate);

        cal.add(Calendar.DATE, -(pDays));

        return localFormat.format(cal.getTime());
    }

    /**
     * pDate날자에서 pMonth만큼 이전 달의 날자를 구한다.
     *
     * @param pDate
     *            기준일
     * @param pMonth
     *            개월수
     * @return 입력된 날자에서 pMonth 이전 달의 날자
     */
    public static String getPrevMonthDate(String pDate, int pMonth) {
        if (pMonth < 0) {
            return pDate;
        }
        SimpleDateFormat localFormat = getLocalFormat(pDate);
        java.util.Calendar cal = toCalendar(pDate);

        cal.add(Calendar.MONTH, -(pMonth));

        return localFormat.format(cal.getTime());
    }

    /**
     * 두 날짜 사이 차이를 반환한다.
     * 1시간 이내인 경우는 **분전 으로 return
     * 1일 이내인 경우는 **시간전으로 return
     * 주 **주 return
     * 월 **월 **일로 return
     *
     * @param Date
     *            date1 //과거
     * @param Date
     *            date2 //미래
     *
     * @param Strin lType
     * 			 		KorType = 한국, EngType = 영문
     *
     * @return String
     */
    public static String dateDiffStr(Date date1, Date date2, String lType) {

        String[] KorType = {"MM월dd일","주전","일전","시간전","분전"};
        String[] EngType = {"M,dd","weeks","days ago","hours ago","min ago"};

        Map lMap = new HashMap();
        lMap.put("KorType", KorType);
        lMap.put("EngType", EngType);

        String[] lanType = (String[]) lMap.get(lType);

        double e = ((double)(date2.getTime()-date1.getTime())/(double)(1000 * 60 * 60 * 24) );
        double e2 = (( date2.getTime() - date1.getTime()) % (1000 * 60 * 60 * 24));

        //e 가 1.0 이상인 경우 1일보다 큰 값이다.
        //e 가 31.0 이상인 경우 1달 이전 이다. e%30
        String str="";

        if(e>=31){
            //해당 월일을 구한다.
            str = format(date1, lanType[0]);
        }

        if(e<31 && e>7.0){
            //주를 구한다. 윤달 계산은 skip
            str = (int)((e/7)+1)+ lanType[1];
        }

        if(e<7.0 && e>1.0){
            //일
            str = (int)e + lanType[2];
        }

        if(e<1.0){
            //1일 이내
            double e3 =  e2 / (1000 * 60 * 60);
            if( e3> 1.0){
                //시간
                str= (int)e3 + lanType[3];
            }

            if(e3< 1.0 && e3 > 0.0){
                double e4 =  e2 / (1000 * 60);
                str = (int)e4 + lanType[4];
            }
        }
        return str;
    }

    /**
     * 날짜 값이 있을 경우만 체크 한다.(날짜의 유효성) "yyyyMMdd" 형식
     * @param date
     * @return
     */
    public static boolean isValidDate(String date) {
        return isValidDate(date, SIMPLEDATEFORMAT);
    }

    /**
     * 날짜 값이 있을 경우만 체크 한다.(날짜의 유효성) "yyyyMMdd" 형식
     * @param date
     * @param format
     * @return
     */
    public static boolean isValidDate(String date, String format) {
        if (StringUtils.isEmpty(date)) return false;
        if (StringUtils.isEmpty(format)) format = SIMPLEDATEFORMAT;
        if(date.length() != format.length()) return false;
        String afterValue = format(parse(date, format), format);
        return date.equals(afterValue);
    }
}

