package mx.edu.tpdm_u3_practica2_15401052

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.net.URL

class MainActivity : AppCompatActivity() {
    var descripcion : EditText ?= null
    var monto : EditText ?= null
    var fechavencimiento : EditText ?= null
    var mostrarConsulta : TextView ?= null
    var pagado : CheckBox ?= null
    var insertar : Button ?= null
    var cargar : Button ?= null
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
        cargar = findViewById(R.id.btnCargar)
        mostrar = findViewById(R.id.btnmostrar)
        mostrarConsulta = findViewById(R.id.mostrarRegistros)

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

        cargar?.setOnClickListener {
            cargar?.setOnClickListener {
                var conexionWeb = ConexionWeb(this)
                conexionWeb.execute(URL("https://cryptic-chamber-14232.herokuapp.com/consultageneralRecibo.php"))
            }
        }

        mostrar?.setOnClickListener {
            val posicion = descripcion?.text.toString().toInt()
            val jsonObject = jsonRegreso.get(posicion)

            mostrarRegistros.setText("Descripción "+jsonObject.getString("descripcion"))
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
