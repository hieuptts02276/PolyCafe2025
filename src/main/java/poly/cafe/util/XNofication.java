package poly.cafe.util;

import javax.swing.JOptionPane;
import java.awt.Component; // Cho các thông báo JOptionPane
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

/**
 * Lớp tiện ích để hiển thị các loại thông báo cho người dùng trong ứng dụng PolyCafe,
 * bao gồm cả thông báo cửa sổ và thông báo khay hệ thống.
 *
 * @author Hieu
 * @version 1.1
 */
public class XNofication {

    // --- Các phương thức hiển thị thông báo JOptionPane (pop-up trong ứng dụng) ---

    /**
     * Hiển thị một thông báo thành công.
     *
     * @param parentComponent Component cha sẽ sở hữu hộp thoại, thường là 'this' từ JFrame/JDialog.
     * @param message Nội dung thông báo.
     */
    public static void showSuccess(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, "Thông Báo Thành Công", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Hiển thị một thông báo lỗi.
     *
     * @param parentComponent Component cha sẽ sở hữu hộp thoại.
     * @param message Nội dung thông báo lỗi.
     */
    public static void showError(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Hiển thị một thông báo cảnh báo.
     *
     * @param parentComponent Component cha sẽ sở hữu hộp thoại.
     * @param message Nội dung thông báo cảnh báo.
     */
    public static void showWarning(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Hiển thị một thông báo chung (kiểu mặc định).
     *
     * @param parentComponent Component cha sẽ sở hữu hộp thoại.
     * @param message Nội dung thông báo.
     * @param title Tiêu đề của hộp thoại.
     */
    public static void showMessage(Component parentComponent, String message, String title) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Hiển thị hộp thoại xác nhận với các tùy chọn CÓ/KHÔNG.
     *
     * @param parentComponent Component cha sẽ sở hữu hộp thoại.
     * @param message Nội dung câu hỏi xác nhận.
     * @return `true` nếu người dùng chọn CÓ, `false` nếu chọn KHÔNG.
     */
    public static boolean confirm(Component parentComponent, String message) {
        int result = JOptionPane.showConfirmDialog(parentComponent, message, "Xác Nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }



    // --- Phương thức gửi thông báo đến khay hệ thống (System Tray) ---

    /**
     * Gửi một thông báo dạng pop-up tới khay hệ thống của người dùng.
     * Lưu ý: Cần có file "icon.png" trong thư mục gốc của dự án hoặc trong một đường dẫn hợp lệ.
     *
     * @param title Tiêu đề của thông báo.
     * @param message Nội dung của thông báo.
     * @param type Kiểu thông báo (INFO, ERROR, WARNING, NONE).
     */
    public static void sendSystemTrayNotification(String title, String message, TrayIcon.MessageType type) {
        // Kiểm tra xem System Tray có được hỗ trợ trên hệ điều hành này không
        if (!SystemTray.isSupported()) {
            System.out.println("System tray not supported on this platform!");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Image image = null;
        try {
            // Cố gắng tải icon. Thay đổi "icon.png" thành đường dẫn icon thực tế của bạn.
            // Đảm bảo icon có kích thước phù hợp (ví dụ: 16x16, 24x24, 32x32)
            image = Toolkit.getDefaultToolkit().createImage("icon.png");
            // Nếu icon không tìm thấy, Toolkit.createImage sẽ trả về Image rỗng,
            // nhưng không ném lỗi tại đây. Lỗi sẽ xảy ra nếu Image rỗng được sử dụng.
        } catch (Exception e) {
            System.err.println("Error loading notification icon: " + e.getMessage());
            // Có thể đặt một icon mặc định hoặc bỏ qua nếu không tải được
            // Nếu không có icon, TrayIcon có thể hiển thị một icon mặc định của Java
        }

        // Kiểm tra nếu image null hoặc rỗng có thể gây lỗi AWTException sau này
        if (image == null) {
             System.err.println("Notification icon could not be loaded. Using default.");
             // Hoặc bạn có thể chọn không gửi thông báo nếu không có icon
             // return;
        }

        TrayIcon trayIcon = new TrayIcon(image != null ? image : new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB), "Thông báo PolyCafe");
        trayIcon.setImageAutoSize(true); // Tự động điều chỉnh kích thước icon
        trayIcon.setToolTip("Ứng dụng quản lý quán cafe"); // Tooltip khi di chuột qua icon

        try {
            tray.add(trayIcon); // Thêm icon vào khay hệ thống
            trayIcon.displayMessage(title, message, type); // Hiển thị thông báo pop-up
            
            // Xóa icon khỏi khay sau khi thông báo được hiển thị.
            // Điều này làm cho icon chỉ xuất hiện tạm thời để hiển thị thông báo.
            // Nếu bạn muốn icon tồn tại vĩnh viễn trong khay, hãy bỏ dòng này.
            tray.remove(trayIcon); 
        } catch (AWTException e) {
            System.err.println("Error adding TrayIcon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gửi thông báo thành công tới khay hệ thống.
     * @param title Tiêu đề.
     * @param message Nội dung.
     */
    public static void showSystemTraySuccess(String title, String message) {
        sendSystemTrayNotification(title, message, TrayIcon.MessageType.INFO);
    }

    /**
     * Gửi thông báo lỗi tới khay hệ thống.
     * @param title Tiêu đề.
     * @param message Nội dung.
     */
    public static void showSystemTrayError(String title, String message) {
        sendSystemTrayNotification(title, message, TrayIcon.MessageType.ERROR);
    }

    /**
     * Gửi thông báo cảnh báo tới khay hệ thống.
     * @param title Tiêu đề.
     * @param message Nội dung.
     */
    public static void showSystemTrayWarning(String title, String message) {
        sendSystemTrayNotification(title, message, TrayIcon.MessageType.WARNING);
    }
}
