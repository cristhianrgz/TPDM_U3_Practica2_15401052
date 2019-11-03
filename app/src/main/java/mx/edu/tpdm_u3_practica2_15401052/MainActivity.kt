package mx.edu.tpdm_u3_practica2_15401052

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.net.URL

class MainActivity : AppCompatActivity() {
    var descripcion : EditText ?= null
    var monto : EditText ?= null
    var fechavencimiento : EditText ?= null
    var pagado : CheckBox ?= null
    var insertar : Button ?= null
    var mostrar : Button ?= null
    var jsonRegreso = ArrayList<org.json.JSONObject>()
    var varPagado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        descripcion = findViewById(R.id.EditDescrip)
        monto = findViewById(R.id.EditMonto)
        fechavencimiento = findViewById(R.id.EditFechaVencimiento)
        pagado = findViewById(R.id.checkpagado)
        insertar = findViewById(R.id.btnInsert)
        mostrar = findViewById(R.id.btnmostrar)

        insertar?.setOnClickListener {
            if(pagado?.isChecked == true){
                Toast.makeText(this,"Elegiste SI", Toast.LENGTH_SHORT).show()
                varPagado = "true"
            }
            else{
                varPagado = "false"
            }

            var conexionWeb = ConexionWeb(this)

            conexionWeb.agregarVariables("descripcion", descripcion?.text.toString())
            conexionWeb.agregarVariables("monto", monto?.text.toString())
            conexionWeb.agregarVariables("fechaVencimiento", fechavencimiento?.text.toString())
            conexionWeb.agregarVariables("pagado", pagado?.text.toString())

            conexionWeb.execute(URL("https://cryptic-chamber-14232.herokuapp.com/insertarRecibo.php"))
        }
    }

    fun mostrarRespuesta(cadena: String){
        //var alerta= AlertDialog.Builder(this)
        var jsonarray = org.json.JSONArray(cadena)
        var total = jsonarray.length()
        //jsonarray.getJSONObject(1).getString("descripcion")
        //jsonarray.getJSONObject(1).getString("FECHA")
        (0..total).forEach {
            jsonRegreso.add(jsonarray.getJSONObject(it))
        }

    }
}
