import java.util.HashMap;

public class TicketOffice {
    private Money amount;
    private Map<Theater, Double> commissionRate = new HashMap<>();
    public TicketOffice(Money amount)
    {
        this.amount = amount;
    }
    boolean contract(Theater theater, Double rate) { }
    boolean cancel(Theater theater) { }
    Money calculateFee(Movie movie, Screening screening, int count)
    {
        return movie.calculateFee(screening, count).multi((double)count);
    }

    Reservation reserve(Theater theater, Movie movie, Screening screening, int count)
    {
        if(!commissionRate.containsKey(theater) || ...) return Reservation.NONE;
        Reservation reservation = theater.reserve(movie, screening, count);
        if(reservation != Reservation.NONE)
        {
            Money sales = calculateFee(movie, screening, count).multi((double)count);
            Money commission = sales.multi(commissionRate.get(theater));
            amount = amount.plus(commission);
            theater.plusAmount(sales.minus(commission));
        }
        return reservation;
    }
}
