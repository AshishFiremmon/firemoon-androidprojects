package Modal;

/**
 * Created by firemoonpc_11 on 12-07-2016.
 */
public class LocationModal {


    String location_id;



    String location_name;


 public LocationModal(String location_id, String location_name)
    {
        this.location_name=location_name;
        this.location_id=location_id;

    }
    public String getLocation_id() {
        return location_id;
    }

    public String getLocation_name() {
        return location_name;
    }


}
