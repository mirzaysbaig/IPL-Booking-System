//package Profinch.ipl.com.services;
//
//import Profinch.ipl.com.entities.Booking;
//import Profinch.ipl.com.repositories.BookingRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.zxing.qrcode.encoder.QRCode;
//import net.glxn.qrgen.image.ImageType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class QrGeneratorService {
//
//    @Autowired
//    BookingRepository bookingRepository;
//
////    @Autowired
////    private  ObjectMapper objectMapper;
//
//    public Booking generateQr(String BookingId){
//       Booking booking =bookingRepository.getBookingByBookingId(BookingId);
//
////      // convert to string for qr code generator ,
////        try {
////            if (booking == null) {
////                throw new IllegalArgumentException("Booking cannot be null");
////            }
////
////            String qrContent = objectMapper.writeValueAsString(booking);
////            byte[] qrCode = QRCode.from(qrContent)
////                    .withSize(250, 250)
////                    .to(ImageType.PNG)
////                    .stream()
////                    .toByteArray();
////            return qrCode;
////        } catch (Exception e) {
////            throw new RuntimeException("Failed to generate QR code", e);
////        }
//    }
//}
