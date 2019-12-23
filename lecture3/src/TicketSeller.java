public class TicketSeller {
    private TicketOffice ticketOffice;
    public void setTicketOffice(TicketOffice ticketOffice)
    {
        this.ticketOffice = ticketOffice;
    }

    Reservation reserve(Customer customer, Theater theater, Movie movie, Screening screening, int count)
    {
        Reservation reservation;
//        Money price = movie.calculateFee(screening, count); 머니때문에 movie 에 대한 의존성이 생겨버렸다.
        Money price = ticketOffice.calculateFee(movie,screening,count);
        if(customer.hasAmount(price))
        {
            reservation = ticketOffice.reserve(theater, movie, screening, count);
            if(reservation != Reservation.NONE) customer.minusAmount(price);
        }
        return reservation;
    }
}
