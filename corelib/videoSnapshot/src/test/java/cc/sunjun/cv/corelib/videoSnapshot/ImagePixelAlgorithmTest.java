package cc.sunjun.cv.corelib.videoSnapshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cc.sunjun.cv.corelib.videoSnapshot.core.ImagePixelAlgorithm;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 图像像素算法测试
 */

public class ImagePixelAlgorithmTest {

	public static void test1() throws IOException{
		BufferedImage imgfile = ImageIO.read(new File("test.jpg"));
		for(int i = 0; i < imgfile.getWidth(); i++) {
			for(int j = 0; j < imgfile.getHeight(); j++) {
				int rgb = imgfile.getRGB(i, j);
				
//				int a = (rgb >> 24) & 0xff;//png图片有此参数
				int r = (rgb & 0xff0000) >> 16;
				int g = (rgb & 0xff00) >> 8;
				int b = (rgb & 0xff);
				
				System.err.println("原始："+ rgb);
				System.err.println("两次转换后：" + ImagePixelAlgorithm.getRGB(r, g, b));
				System.err.println("红绿蓝：" + r + "," + g + "," + b);
				byte[] rgbarr = ImagePixelAlgorithm.convertRGB(rgb);
				System.err.println("一次转换后：" + rgbarr[0] + "," + rgbarr[1] + "," + rgbarr[2]);

			}
		}
	}
	public static void main(String[] args) throws IOException {
		 test1();
	}
	
}
