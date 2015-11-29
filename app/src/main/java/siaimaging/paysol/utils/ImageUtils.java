package siaimaging.paysol.utils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Created by U^MAR on 11/22/2015.
 */
public class ImageUtils {
    public static MatOfFloat featureExtraction(Mat inImg) {

        final HOGDescriptor hog = new HOGDescriptor();
        final MatOfFloat descriptors = new MatOfFloat();

        // pre-processing
        final Mat grayImg = new Mat();
        Imgproc.cvtColor(inImg, grayImg, Imgproc.COLOR_RGB2GRAY);
        grayImg.convertTo(grayImg, CvType.CV_32F);

        // Mean normalization
        Scalar meanVal = Core.mean(grayImg);
        Core.subtract(grayImg, meanVal, grayImg);

        // median filtering
        Imgproc.medianBlur(grayImg, grayImg, 5);

        // Difference of Gaussian Filtering

        // compute HoG
        hog.compute(grayImg, descriptors);
        return descriptors;
    }

    public static double compareFeatures(MatOfFloat desc1, MatOfFloat desc2) {
        double norm1 = Core.norm(desc1, Core.NORM_L2);
        double norm2 = Core.norm(desc2, Core.NORM_L2);

        final MatOfFloat mul = new MatOfFloat();
        Core.multiply(desc1, desc2, mul);
        Scalar dot_prod = Core.sumElems(mul);

        // return cosine distance between both matrices
        return dot_prod.val[0] / (norm1*norm2);
    }

    public static boolean classify(double dist){
        double thresh = 0.5;
        //FIXME: threshold has to be adapted empirically
        return dist < thresh;
    }
}
