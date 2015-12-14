package enable.tum.tum_enable_app;

import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import enable.tum.tum_enable_app.ProductHandling.Category;
import enable.tum.tum_enable_app.ProductHandling.Product;
import enable.tum.tum_enable_app.ProductHandling.ProgramLogicSingleton;

/**
 * Created by Lennart Mittag on 05.12.2015.
 */
public class FragmentFeedbackAreaTablet extends Fragment
        implements View.OnClickListener,
        ActivityOrderingScreen.OnIncomingOrderListener {

    private static final String TAG = "FragmentFAT";

    private boolean[] actualTablet = new boolean[6];
    private Button bezahlen;

    @Override
    public void onCreate(Bundle saverdInstacesState) {
        super.onCreate(saverdInstacesState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View v = inflater.inflate(R.layout.layout_fragment_feedback_area_tablet, container, false); //a true would show the layout now, we are doing thsi in the ActivityOrderingScreen

        for (int i = 0; i < actualTablet.length; i++) {
            actualTablet[i] = false;
        }

        Log.d(TAG, "bla");
        return v;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bezahlen = (Button) getActivity().findViewById(R.id.bezahlen);
        bezahlen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on Click bezahlen");

                DialogFragment bestätigungBezahlen = new PaymentDialogFragment();
                bestätigungBezahlen.show(getFragmentManager(), "payment");
            }
        });
    }

    @Override
    public void onIncomingOrder(int id, Category category) {
        ProgramLogicSingleton instance = ProgramLogicSingleton.getInstance();

        Product product = null;

        if (category == Category.healthy) {
            product = instance.getHealthyProducts().get(id);
        } else {
            product = instance.getUnhealthyProducts().get(id);
        }

        instance.addProductToActualOrder(product);

        ImageButton btnImg = null;

        if (!actualTablet[0]) {
            btnImg = (ImageButton) getActivity().findViewById(R.id.button);
            actualTablet[0] = true;
        } else if (!actualTablet[1]) {
            btnImg = (ImageButton) getActivity().findViewById(R.id.button2);
            actualTablet[1] = true;
        } else if (!actualTablet[2]) {
            btnImg = (ImageButton) getActivity().findViewById(R.id.button3);
            actualTablet[2] = true;
        } else if (!actualTablet[3]) {
            btnImg = (ImageButton) getActivity().findViewById(R.id.button4);
            actualTablet[3] = true;
        } else if (!actualTablet[4]) {
            btnImg = (ImageButton) getActivity().findViewById(R.id.button5);
            actualTablet[4] = true;
        } else if (!actualTablet[5]) {
            btnImg = (ImageButton) getActivity().findViewById(R.id.button6);
            actualTablet[5] = true;
        } else {
            return;
        }

        btnImg.setOnClickListener(this);

        btnImg.setTag(product);
        btnImg.setImageResource(product.getPathPicture());
        btnImg.setBackgroundColor(Color.WHITE);
        btnImg.invalidate();
    }

    @Override
    public void onClick(View v) {
        ImageButton imgBtn = (ImageButton) v;
        imgBtn.setImageResource(R.drawable.mcdonalds_golden_arches);
        imgBtn.setBackgroundColor(Color.WHITE);
        imgBtn.setOnClickListener(null);
        imgBtn.invalidate();

        if (v.getId() == R.id.button) {
            actualTablet[0] = false;
        } else if (v.getId() == R.id.button2) {
            actualTablet[1] = false;
        } else if (v.getId() == R.id.button3) {
            actualTablet[2] = false;
        } else if (v.getId() == R.id.button4) {
            actualTablet[3] = false;
        } else if (v.getId() == R.id.button5) {
            actualTablet[4] = false;
        } else if (v.getId() == R.id.button6) {
            actualTablet[5] = false;
        }

        Product removedProduct = (Product) imgBtn.getTag();
        ProgramLogicSingleton instance = ProgramLogicSingleton.getInstance();
        instance.removeProductFromActualOrder(removedProduct);

        updatePrice();
    }

    public void updatePrice() {
        ProgramLogicSingleton instance = ProgramLogicSingleton.getInstance();
        TextView gesamtpreis = (TextView) getActivity().findViewById(R.id.gesamtpreis);
        gesamtpreis.setText("Gesamtpreis: " + Math.round(instance.getPriceOfOrder() * 100.0) / 100.0 + "€");
    }
}
