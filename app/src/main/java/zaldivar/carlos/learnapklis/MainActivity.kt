package zaldivar.carlos.learnapklis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.baoyachi.stepview.VerticalStepView
import cu.uci.apklischeckpayment.Verify

const val PACKAGE_ID = "zaldivar.carlos.calcelect" //nombre del paquete como esta en apklis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Variables utilizadas para recivir respiesta desde el api
        val response = Verify.isPurchased(this, PACKAGE_ID)
        val userName = response.second
        val isPaid = response.first


        // Crear vista de lista para los requerimientos
        var myStepView: VerticalStepView = findViewById(R.id.myStepView)
        // Crear lista
        var list0: MutableList<String> = ArrayList()
        // Variable que guardara posicion activa
        var option = 0

        // Creando valores de la lista dependiendo de estado de compra
        when{
            userName.isNullOrBlank() ->{
                option = 0
                list0.add("Debes estar autenticado")
                list0.add("Debes compara la aplicacion")
                list0.add("Compra verificada")
            }
            !isPaid ->{
                option = 1
                list0.add("Usted esta autenticado correctamente")
                list0.add("Debe comprar la aplicación")
                list0.add("Compra verificada")
            }
            isPaid ->{
                option = 2
                list0.add("Usted esta autenticado correctamente")
                list0.add("Ya ha comprado la aplicación")
                list0.add("Compra verificada, Usted puede continuar")
            }
        }

        // Formatear quien StepView
        myStepView.setStepsViewIndicatorComplectingPosition(option)
            .reverseDraw(false)
            .setTextSize(16)
            .setStepViewTexts(list0)
            .setStepsViewIndicatorCompletedLineColor(
                ContextCompat.getColor(
                    this,
                    R.color.primaryTextColor
                )
            )
            .setStepsViewIndicatorUnCompletedLineColor(
                ContextCompat.getColor(
                    this,
                    R.color.primaryTextColor
                )
            )
            .setStepViewComplectedTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.black
                )
            )
            .setStepViewUnComplectedTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.black
                )
            )
            .setStepsViewIndicatorCompleteIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_check
                )
            )
            .setStepsViewIndicatorDefaultIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_unchecked
                )
            )
            .setStepsViewIndicatorAttentionIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_info
                )
            )

    }
}