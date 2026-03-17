package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Toolbar 위임
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title = "Navigation 아키텍처"

        // 2. Toolbar 내부의 햄버거 아이콘 UI와 실제 DrawerLayout의 열림/닫힘 상태 머신을 동기화하는 리스너 설정
        // 접근성(스크린 리더 TTS 등)을 위해 열림/닫힘 상태에 대한 문자열 리소스 참조가 필수 파라미터로 요구됩니다.
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.myToolbar,
            R.string.nav_open,  // res/values/strings.xml에 <string name="nav_open">서랍 열기</string> 추가 필요
            R.string.nav_close // res/values/strings.xml에 <string name="nav_close">서랍 닫기</string> 추가 필요
        )

        // 물리적 Edge Swipe 이벤트를 수신하도록 DrawerLayout에 리스너 부착
        binding.drawerLayout.addDrawerListener(toggle)
        // 현재 Drawer의 실제 물리적 상태를 읽어와 상단 햄버거 아이콘 모양(≡ 또는 ←)을 즉시 동기화
        toggle.syncState()

        // 3. 서랍 메뉴 항목 터치 이벤트 라우팅
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> Toast.makeText(this, "메인 화면 트랜지션 실행", Toast.LENGTH_SHORT).show()
                R.id.nav_profile -> Toast.makeText(this, "프로필 뷰로 교체", Toast.LENGTH_SHORT).show()
            }
            // 메뉴 항목을 탭한 후에는 사용자 편의성(UX 가이드라인)을 위해 서랍(Drawer)을 자동으로 닫습니다.
            binding.drawerLayout.closeDrawers()
            true // 터치 이벤트가 성공적으로 처리(Consumed)되었음을 프레임워크에 알림
        }
    }
}