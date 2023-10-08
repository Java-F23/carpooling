package Repositories;

import Models.Ride;
import Filters.RideFilters;

import java.util.ArrayList;

public class RideRepository extends GenericRepository<Ride>{
    public ArrayList<Ride> getByFilter(RideFilters criteria) {
        ArrayList<Ride> filteredRides = new ArrayList<Ride>();
        for(Ride r : data){
            if(!criteria.Region.isEmpty()  && criteria.startTime != 0){
                if(criteria.Region.equals(r.getRoute().get(0).getRegion()) && criteria.startTime==r.getStartTime()){
                    filteredRides.add(r);
                }
            } else if (criteria.startTime!=0) {
                if(criteria.startTime == r.getStartTime()){
                    filteredRides.add(r);
                }
            } else if (!criteria.Region.isEmpty()) {
                if(criteria.Region.equals((r.getRoute().get(0).getRegion()))){
                    filteredRides.add(r);
                }
            }
        }
        return filteredRides;
    }
}