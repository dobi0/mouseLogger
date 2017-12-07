package capture2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class Main implements NativeMouseInputListener, NativeMouseWheelListener {

	private SampleTask2 sampletask2 = null;
	private boolean record = false;
	private RecordAudio recordAudio = null;
	private long beforeTime = 10;
	private long currentTime = 0;

	public Main() {
		this.mkdir();
	}

	public void mkdir(){
		String folder_name = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(Calendar.getInstance().getTime());
		this.sampletask2 = new SampleTask2(folder_name);
		this.sampletask2.trun();
		this.sampletask2.run();
		RecordAudio recordAudio = new RecordAudio(folder_name);
 	    this.recordAudio = recordAudio;
 	    try {
  		    System.out.printf("record\n");
  		    recordAudio.startRecord();
			this.record = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}

    // マウスをクリックした
    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
    	  long currentTime = System.currentTimeMillis();
   	   this.currentTime = currentTime;
   	   if(this.currentTime - this.beforeTime > 3 * 1000){
   		 this.sampletask2.run();
   	   }

   	 System.out.println("currentTime" + currentTime + "秒beforeTime" + beforeTime);
   	 this.beforeTime = currentTime;
        System.out.println(e.paramString());
    }

    // マウスを押した
    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println(e.paramString());
    }

    // マウスを離した
    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        System.out.println(e.paramString());
    }

    // マウスを移動した
    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        System.out.println(e.paramString());
    }

    // マウスをドラッグした
    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        System.out.println(e.paramString());
    }

    // マウスホイールを動かした
    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        System.out.println(e.paramString());
    }

    public static void main(String[] args) {
        // フックしてなかったらフック
        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // ログを抑制してハンドラを登録
        //suppressLogger();


        GlobalScreen.addNativeMouseListener(new Main());
        GlobalScreen.addNativeMouseMotionListener(new Main());
        GlobalScreen.addNativeMouseWheelListener(new Main());
        //final GlobalScreen globalScreen = GlobalScreen.getInstance();
        //globalScreen.addNativeMouseListener(handler);
        //globalScreen.addNativeMouseMotionListener(handler);
        //globalScreen.addNativeMouseWheelListener(handler);
    }

    // suppressLogger() は省略
}
