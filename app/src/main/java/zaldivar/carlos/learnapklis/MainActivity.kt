package zaldivar.carlos.learnapklis

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.baoyachi.stepview.VerticalStepView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import cu.uci.apklischeckpayment.Verify

const val PACKAGE_ID = "zaldivar.carlos.learnapklis" //nombre del paquete como esta en apklis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Variables utilizadas para resivir respuesta desde el api
        val response = Verify.isPurchased(this, PACKAGE_ID)
        val userName = response.second
        val isPaid = response.first

        /**
         * Crear vista de lista para los requerimientos
         */

        var myStepView: VerticalStepView = findViewById(R.id.myStepView)
        // Crear lista
        var list0: MutableList<String> = ArrayList()
        // Variable que guardara posicion activa
        var option = 0

        /**
         * Creando valores de la lista dependiendo de estado de compra
         */

        when {
            userName.isNullOrBlank() -> {
                option = 0
                list0.add("Debes estar autenticado")
                list0.add("Debes compara la aplicacion")
                list0.add("Compra verificada")
            }
            !isPaid -> {
                option = 1
                list0.add("Usted esta autenticado correctamente")
                list0.add("Debe comprar la aplicación")
                list0.add("Compra verificada")
            }
            isPaid -> {
                option = 2
                list0.add("Usted esta autenticado correctamente")
                list0.add("Ya ha comprado la aplicación")
                list0.add("Compra verificada, Usted puede continuar")
            }
        }

        /**
         * Formatear quien StepView
         */
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

        /**
         * De aquí para abajo es la parte del Slide Show
         */

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.baner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.baner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.baner2, ScaleTypes.FIT))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slide)
        imageSlider.setImageList(imageList)

        /**
         *  Usar click listener para enlazar a aplicaciones en apklis.
         */
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                when (position) {
                    0 -> {
                        val uri = Uri.parse("https://www.apklis.cu/application/cu.apklis.unehogar")
                        rateAction(uri)
                    }
                    1 -> {
                        val uri =
                            Uri.parse("https://www.apklis.cu/application/zaldivar.carlos.calcelect")
                        rateAction(uri)
                    }
                    2 -> {
                        val uri = Uri.parse("https://www.apklis.cu/application/cu.cubava.hidro")
                        rateAction(uri)
                    }
                }
            }
        })
    }

    /**
     * FUNCION PARA REENVIAR A APKLIS A VALORAR APP
     */
    fun rateAction(uri: Uri) {
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(uri.toString())
                )
            )
        }
    }
}