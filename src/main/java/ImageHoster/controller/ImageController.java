package ImageHoster.controller;

//import ImageHoster.HardCodedImage;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

//    @Autowired
//    private HardCodedImage hardCodedImage;

    //This method displays all the images in the user home page after successful login
    @RequestMapping("images")
    public String getUserImages(Model model) {
        //Complete the method
        //Get all the hard-coded images in the application using getAllImages() method in ImageService class and add them to the model with 'images' as the key

        List<Image> images = imageService.getAllImages();

        model.addAttribute("images",images);

        return "images";
    }

    //This method is called when the details of the specific image with corresponding title are to be displayed
    //The logic is to get the image from the databse with corresponding title. After getting the image from the database the details are shown
    //But since the images are not stored in the database, therefore, we have hard-coded two images here
    //If the title of the image is 'Dr. Strange', an image object is created with all the corresponding details
    //If the title of the image is 'SpiderMan', an image object is created with all the corresponding details
    //The image object is added to the model and 'images/image.html' file is returned
//    @RequestMapping("/images/{title}")
//    public String showImage(Model model){ //Receive the dynamic variable in the URL '/images/{title}' in a string variable named 'title' and the Model type object named 'model') {
//        Date date = new Date();
//        Image image = new Image();
//        if (title.equals("Dr. Strange")) {
//            image = new Image(1, "Dr. Strange",hardCodedImage.getDrStrange(),"Dr. Strange has a time stone", date);
//        } else if (title.equals("SpiderMan")) {
//            image = new Image(2, "SpiderMan", hardCodedImage.getSpiderMan(), "Spider man dies in Infinity War",date);
//        }
//
//        model.addAttribute("images",image);
//        return 'images/image';
//        //Add the image in the model image the kay as 'image'
//        //Return 'images/image.html' file
//    }

    //This method is called when the details of the specific image with corresponding title are to be displayed
    //The logic is to get the image from the databse with corresponding title. After getting the image from the database the details are shown
    //But since the images are not stored in the database, therefore, we have hard-coded two images here
    //If the title of the image is 'Dr. Strange', an image object is created with all the corresponding details
    //If the title of the image is 'SpiderMan', an image object is created with all the corresponding details
    //The image object is added to the model and 'images/image.html' file is returned
    @RequestMapping("/images/{title}")
    public String showImage(@PathVariable("title") String title, Model model, HttpSession session) {
        Image image = imageService.getImageByTitle(title);

       /* User user = (User)session.getAttribute("loggeduser");
        Date date = new Date();
        Image image = null;
        if (title.equals("Dr. Strange")) {
            image = new Image(1, "Dr. Strange", hardCodedImage.getDrStrange(), "Dr. Strange has a time stone", date, user);
        } else if (title.equals("SpiderMan")) {
            image = new Image(2, "SpiderMan", hardCodedImage.getSpiderMan(), "Spider man dies in Infinity War", date, user);
        }*/

        model.addAttribute("image", image);
        return "images/image";
    }

//    public HardCodedImage getHardCodedImage() {
//        return hardCodedImage;
//    }
//
//    public void setHardCodedImage(HardCodedImage hardCodedImage) {
//        this.hardCodedImage = hardCodedImage;
//    }

    //This controller method is called when the request pattern is of type 'images/upload'
    //The method returns 'images/upload.html' file
    @RequestMapping("/images/upload")
    public String newImage() {
        return "images/upload";
    }

    //This controller method is called when the request pattern is of type 'images/upload' and also the incoming request is of POST type
    //The method receives all the details of the image to be stored in the database, but currently we are not using database so the business logic simply retuns null and does not store anything in the database
    //After you get the imageFile, convert it to Base64 format and store it as a string
    //After storing the image, this method directs to the logged in user homepage displaying all the images
    @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
    public String createImage(@RequestParam("file") MultipartFile file, Image newImage , HttpSession session) throws IOException {

        String uploadedImageData = convertUploadedFileToBase64(file);
        newImage.setImageFile(uploadedImageData);
        newImage.setDate(new Date());

        User user = (User)session.getAttribute("loggeduser");
        newImage.setUser(user);
        imageService.uploadImage(newImage);
        //Complete the method
        //Encode the imageFile to Base64 format and set it as the imageFile attribute of the newImage
        //Set the date attribute of newImage
        //Call the business logic to upload an image which currently does not store the image in the database
        //After uploading the image direct to the logged in user homepage displaying all the images

        return "redirect:/images";
    }

    //This controller method is called when the request pattern is of type 'editImage'
    //This method fetches the image with the corresponding id from the database and adds it to the model with the key as 'image'
    //The method then returns 'images/edit.html' file wherein you fill all the updated details of the image
    @RequestMapping(value = "/editImage")
    public String editImage(@RequestParam("imageId") Integer imageId, Model model) {
        Image image = imageService.getImage(imageId);
        model.addAttribute("image", image);
        return "images/edit";
    }

    //This controller method is called when the request pattern is of type 'images/edit' and also the incoming request is of PUT type
    //The method receives the imageFile, imageId, updated image, along with the Http Session
    //The method adds the new imageFile to the updated image if user updates the imageFile and adds the previous imageFile to the new updated image if user does not choose to update the imageFile
    //Set an id of the new updated image
    //Set the user using Http Session
    //Set the date on which the image is posted
    //Call the updateImage() method in the business logic to update the image
    //Direct to the same page showing the details of that particular updated image
    @RequestMapping(value = "/editImage", method = RequestMethod.PUT)
    public String editImageSubmit(@RequestParam("file") MultipartFile file, @RequestParam("imageId") Integer imageId, Image updatedImage, HttpSession session) throws IOException {

        //Complete the method
        String uploadedImageData = convertUploadedFileToBase64(file);
        updatedImage.setImageFile(uploadedImageData);
        updatedImage.setId(imageId);
        updatedImage.setDate(new Date());
        User user = (User)session.getAttribute("loggeduser");
        updatedImage.setUser(user);
        imageService.updateImage(updatedImage);
        return "redirect:/images";
    }

    //This method converts the image to Base64 format
    private String convertUploadedFileToBase64(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }
}
