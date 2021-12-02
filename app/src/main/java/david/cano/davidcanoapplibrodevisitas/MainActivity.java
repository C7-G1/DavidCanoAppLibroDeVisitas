package david.cano.davidcanoapplibrodevisitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private final static String NOMBRE_FICHERO = "visitantes.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et;
        et=(EditText) findViewById(R.id.etNombre);
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    //Ha sido"intro"
                    onNuevoNombre();
                    return true;
                }
                return false;
            }});

        actualizarVisitantes();
        }
    protected void onNuevoNombre(){
        EditText et;
        et=(EditText) findViewById(R.id.etNombre);
        String nuevoNombre;
        nuevoNombre=et.getText().toString();
        if(nuevoNombre.trim().equals("")){
            muestraMensaje(R.string.errorNombre);
            return;
        }
        try{
            FileOutputStream fos;
            //abre el fichero de escritura, para añadir datos por el final.
            fos=openFileOutput(NOMBRE_FICHERO, Context.MODE_PRIVATE|Context.MODE_APPEND);
            java.io.OutputStreamWriter out;
            out=new OutputStreamWriter(fos);
            out.write(nuevoNombre+"\n");
            out.close();
            muestraMensaje(R.string.bienvenido);
               actualizarVisitantes();

        } catch (Exception e) {
            muestraMensaje(R.string.errorRegistro);
        }
        InputMethodManager imm;
        imm = (InputMethodManager)getSystemService (Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et.requestFocus();
        et.setText("");
    } // onNuevoNombre

    public void actualizarVisitantes(){
        StringBuffer strBuf=new StringBuffer();
        TextView tv;
        tv=(TextView) findViewById(R.id.etNombre);
        try {
            String visitante;
            FileInputStream fis;
            fis = openFileInput(NOMBRE_FICHERO);
            Scanner scanner = new Scanner(fis);
            if (scanner.hasNextLine()) {
                visitante = scanner.nextLine();
                strBuf.append(visitante);
            }
            while (scanner.hasNextLine()) {
                visitante = scanner.nextLine();
                strBuf.append("\n" + visitante);
            }//While
            scanner.close();
            tv.setText(strBuf.toString());
        }catch (Exception e) {
// Algo fue mal :-(
                muestraMensaje(R.string.errorVisitantes);
            } // try-catch
        } // actualizarVisitantes
    protected void muestraMensaje(int id) {
        Toast t;
        String mensaje;
        mensaje = getResources().getString(id);
        t = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        t.show();
    } // muestraMensaje


}


