package vn.udn.ute.week08.s2415053122117

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private fun isValidName(name: String): Boolean = name.length in 1..40

    private fun parseScore(raw: String): Double? {
        val s = raw.trim()
        if(s.isEmpty() || s.contains(",")) return null
        val v = s.toDoubleOrNull() ?: return null
        return if (v.isFinite() && v in 0.0..10.0) v else null
    }

    private fun calcT(qt: Double, ck: Double): Double = 0.4 * qt + 0.6 * ck

    private fun classify(t: Double, ck: Double): String {
        val d = 5.5
        return if (t >= d && ck >= 3.0) "Đạt" else "Chưa đạt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtTen = findViewById<EditText>(R.id.edtTen)
        val edtQT = findViewById<EditText>(R.id.edtQT)
        val edtCK = findViewById<EditText>(R.id.edtCK)
        val btnKiemTra = findViewById<Button>(R.id.btnKiemTra)
        val btnXoa = findViewById<Button>(R.id.btnXoa)
        val tvKetQua = findViewById<TextView>(R.id.tvKetQua)

        btnKiemTra.setOnClickListener {
            edtTen.error = null
            edtQT.error = null
            edtCK.error = null

            val ten = edtTen.text.toString().trim()
            val qt = parseScore(edtQT.text.toString())
            val ck = parseScore(edtCK.text.toString())

            var valid = true
            if (!isValidName(ten)){
                edtTen.error = "Tên sau trim phải từ 1..40 ký tự"
                valid = false
            }
            if(qt == null){
                edtQT.error = "Nhập số thực 0..10 (dùng dấu chấm)"
                valid = false
            }
            if(ck == null){
                edtCK.error = "Nhập số thực 0..10 (dùng dấu chấm)"
                valid = false
            }

            if(!valid){
                tvKetQua.text = ""
                return@setOnClickListener
            }

            val t = calcT(qt!!, ck!!)
            val ketLuan = classify(t, ck)
            tvKetQua.text = String.format(
                Locale.US,
                "Học phần: %s\nQT: %.2f | CK: %.2f\nĐiểm T: %.2f\nKết lận: %s",
                ten, qt, ck, t, ketLuan
            )
        }

        btnXoa.setOnClickListener {
            edtTen.text.clear()
            edtQT.text.clear()
            edtCK.text.clear()
            edtTen.error = null
            edtQT.error = null
            edtCK.error = null
            tvKetQua.text = ""
        }
    }
}