package kr.co.company.aunae;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.RotationController;
import com.google.ar.sceneform.ux.ScaleController;
import com.google.ar.sceneform.ux.TransformableNode;

public class ARPlayActivity extends AppCompatActivity {

    private static final String TAG = ARPlayActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.1;
    private final String DTAG = "arplay";
    private String modelName; // intent data

    private int modelAddress;
    private boolean isBuilding = false;
    private int scaleSizeNum = 0;   // 0일때 큰 건물, 1일 때 작은 건물
    private TextView scaleControllerTextView;
    // arModel clear
    private TransformableNode tfModel;
    private AnchorNode anchorNode;
    private Anchor anchor = null;
    ////////////////////////////////////////////
    private int modelIndex; // intent data

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;


    ////////////////////////////////////////////

    private static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            Log.e(TAG, "Sceneform requires Android N or later");
//            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_SHORT).show();
//            activity.finish();
//            return false;
//        }
        String openGlVersrionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersrionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.1 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.1 later", Toast.LENGTH_SHORT).show();
            activity.finish();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_arplay);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        scaleControllerTextView = findViewById(R.id.scaleControllerTextView);

        Intent homeIntent = getIntent();
        modelIndex = homeIntent.getIntExtra("index", -1);
        // intent error
        if (modelIndex == -1) {
            Log.d("ERROR", "ERROR : modelIndex = -1");
            finish();
        }

        modelAddress = Constants.modelID.get(modelIndex);

        if (modelIndex == 2 || modelIndex == 3) {
            scaleControllerTextView.setText("미니어처로 보기");
            isBuilding = true;
        }
        Log.d(DTAG, "model address");


        ModelRenderable.builder()
                .setSource(this, modelAddress)
//                .setSource(this, modelID.get(modelIndex))
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast = Toast.makeText(this, "Unable to load an renderable", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        }
                );
        Log.d(DTAG, "model build");

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (modelRenderable == null || anchor != null) {
                        return;
                    }

                    // Create the Annchor
                    anchor = hitResult.createAnchor();
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable gun and add it to the anchor
                    tfModel = new TransformableNode(arFragment.getTransformationSystem());
                    tfModel.setParent(anchorNode);
                    tfModel.setRenderable(modelRenderable);
                    tfModel.select();
                    if (scaleSizeNum == 1) {
                        ScaleController scl = tfModel.getScaleController();
                        scl.setMinScale((float) 0.1);
                        scl.setMaxScale((float) 0.3);
                    }

                    if (modelName.equals("house")) {
                        RotationController rcl = tfModel.getRotationController();
                        rcl.setRotationRateDegrees(rcl.getRotationRateDegrees() + 20f);
                    }

                    if (modelName.equals("house")) {
                        RotationController rcl = tfModel.getRotationController();
                        rcl.setRotationRateDegrees(rcl.getRotationRateDegrees() + 20f);
                    }
                }
        );
    }

    // onClick function
    public void playClick(View view) {
        switch (view.getId()) {
            case R.id.clearImageView:
                if (anchor == null) {
                    return;
                } else {
                    tfModel.onDeactivate();
                    tfModel = null;
                    anchorNode.onDeactivate();
                    anchorNode = null;
                    anchor.detach();
                    anchor = null;
                }
                break;
            case R.id.scaleControllerTextView:
                if (isBuilding && scaleSizeNum == 0) {
                    scaleSizeNum = 1;
                    scaleControllerTextView.setText("큰 건물로 보기");
                    if (anchor == null) {
                        break;
                    } else {
                        tfModel.onDeactivate();
                        tfModel = null;
                        anchorNode.onDeactivate();
                        anchorNode = null;
                        anchor.detach();
                        anchor = null;
                    }
                } else if (isBuilding && scaleSizeNum == 1) {
                    scaleSizeNum = 0;
                    scaleControllerTextView.setText("미니어쳐로 보기");
                    if (anchor == null) {
                        break;
                    } else {
                        tfModel.onDeactivate();
                        tfModel = null;
                        anchorNode.onDeactivate();
                        anchorNode = null;
                        anchor.detach();
                        anchor = null;
                    }
                }
                break;
            case R.id.finishARImageView:
                finish();
                break;
            default:
                break;
        }
    }
}
