package com.google.ar.core.examples.java.common.helpers;

import android.graphics.Point;

import com.google.ar.core.Anchor;
import com.google.ar.core.Camera;
import com.google.ar.core.Frame;

import android.opengl.Matrix;

public class ARHelper {

    public static Point anchorToScreenPoint(Camera camera, Anchor anchor, float screenWidth, float screenHeight) {
        // Lấy ma trận chiếu từ camera của session
        float[] projectionMatrix = new float[16];
        camera.getProjectionMatrix(projectionMatrix, 0, 0.1f, 100.0f); // khoảng cách gần và xa

        // Lấy tọa độ 3D của anchor
        float[] anchorMatrix = new float[16];
        anchor.getPose().toMatrix(anchorMatrix, 0);

        float[] anchorPosition = new float[4];
        anchorPosition[0] = anchorMatrix[12];  // X
        anchorPosition[1] = anchorMatrix[13];  // Y
        anchorPosition[2] = anchorMatrix[14];  // Z
        anchorPosition[3] = 1.0f;

        // Tính toán tọa độ 2D trên màn hình
        float[] screenPos = new float[2];
        Matrix.multiplyMV(screenPos, 0, projectionMatrix, 0, anchorPosition, 0);

        // Chuẩn hóa để có được tọa độ màn hình
        float x = screenPos[0] / screenPos[3];
        float y = screenPos[1] / screenPos[3];

        // Chuyển đổi sang tọa độ màn hình thực
        float displayX = screenWidth * (x + 1.0f) / 2.0f;
        float displayY = screenHeight * (1.0f - y) / 2.0f;

        // Trả về điểm Point chứa tọa độ màn hình
        return new Point((int) displayX, (int) displayY);
    }
}
