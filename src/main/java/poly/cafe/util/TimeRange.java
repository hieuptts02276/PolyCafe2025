package poly.cafe.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TimeRange {

    private Date begin;
    private Date end;

    // Constructor public dùng cho Date
    public TimeRange(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
    }

    // Constructor private chuyển đổi từ LocalDate sang Date
    private TimeRange(LocalDate begin, LocalDate end) {
        this(Date.from(begin.atStartOfDay(ZoneId.systemDefault()).toInstant()),
             Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    // Getter và Setter cho begin
    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    // Getter và Setter cho end
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    // Khoảng thời gian hôm nay: từ hôm nay đến ngày mai
    public static TimeRange today() {
        LocalDate begin = LocalDate.now();
        LocalDate end = begin.plusDays(1);
        return new TimeRange(begin, end);
    }

    // Khoảng thời gian tuần này: từ thứ Hai tuần này đến thứ Hai tuần sau
    public static TimeRange thisWeek() {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDate end = begin.plusDays(7);
        return new TimeRange(begin, end);
    }

    // Khoảng thời gian tháng này: từ ngày 1 tháng này đến ngày 1 tháng sau
    public static TimeRange thisMonth() {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.withDayOfMonth(1);
        LocalDate end = begin.plusMonths(1);
        return new TimeRange(begin, end);
    }

    // Khoảng thời gian quý này: từ ngày 1 tháng đầu quý đến ngày 1 tháng đầu quý kế tiếp
    public static TimeRange thisQuarter() {
        LocalDate now = LocalDate.now();
        int firstMonth = now.getMonth().firstMonthOfQuarter().getValue();
        LocalDate begin = now.withMonth(firstMonth).withDayOfMonth(1);
        LocalDate end = begin.plusMonths(3);
        return new TimeRange(begin, end);
    }

    // Khoảng thời gian năm này: từ ngày 1 tháng 1 đến ngày 1 tháng 1 năm sau
    public static TimeRange thisYear() {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.withMonth(1).withDayOfMonth(1);
        LocalDate end = begin.plusYears(1);
        return new TimeRange(begin, end);
    }
}
