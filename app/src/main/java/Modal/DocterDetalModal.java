package Modal;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by firemoonpc_11 on 12-07-2016.
 */
public class DocterDetalModal {

    String doctor_id;



    String clinic_add;
    String doctor_name;
    String doctor_img_url;



    private  LatLng doctor_clinic_latlong ;


 public    DocterDetalModal(String doctor_id,String doctor_name,String clinic_add,String doctor_img_url,LatLng doctor_clinic_latlong)
    {
        this.doctor_name=doctor_name;
        this.clinic_add=clinic_add;
        this.doctor_img_url=doctor_img_url;
        this.doctor_clinic_latlong=doctor_clinic_latlong;
    }

    public String getClinic_add() {
        return clinic_add;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getDoctor_img_url() {
        return doctor_img_url;
    }
    public LatLng getDoctor_clinic_latlong() {
        return doctor_clinic_latlong;
    }
}
