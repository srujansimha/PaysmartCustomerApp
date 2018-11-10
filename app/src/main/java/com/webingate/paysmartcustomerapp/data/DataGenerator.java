package com.webingate.paysmartcustomerapp.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.content.res.AppCompatResources;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.customerappFAQMainMenuActivity;
import com.webingate.paysmartcustomerapp.customerapp.TravelModel;
import com.webingate.paysmartcustomerapp.model.BookingType;
import com.webingate.paysmartcustomerapp.model.MusicAlbum;
import com.webingate.paysmartcustomerapp.model.CardViewImg;
import com.webingate.paysmartcustomerapp.model.Image;
import com.webingate.paysmartcustomerapp.model.Inbox;
import com.webingate.paysmartcustomerapp.model.MusicSong;
import com.webingate.paysmartcustomerapp.model.People;
import com.webingate.paysmartcustomerapp.model.ShopCategory;
import com.webingate.paysmartcustomerapp.model.ShopProduct;
import com.webingate.paysmartcustomerapp.model.Social;
import com.webingate.paysmartcustomerapp.utils.MaterialColor;
import com.webingate.paysmartcustomerapp.utils.Tools;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ArrayList;
@SuppressWarnings("ResourceType")
public class DataGenerator {

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static List<String> getStringsShort(Context ctx) {
        List<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_short);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }

    public static List<Integer> getNatureImages(Context ctx) {
        List<Integer> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.sample_images);
        for (int i = 0; i < drw_arr.length(); i++) {
            items.add(drw_arr.getResourceId(i, -1));
        }
        Collections.shuffle(items);
        return items;
    }

    public static List<String> getStringsMonth(Context ctx) {
        List<String> items = new ArrayList<>();
        String arr[] = ctx.getResources().getStringArray(R.array.month);
        for (String s : arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }

    /**
     * Generate dummy data CardViewImg
     *
     * @param ctx   android context
     * @param count total result data
     * @return list of object
     */
    public static List<CardViewImg> getCardImageData(Context ctx, int count) {

        List<CardViewImg> items = new ArrayList<>();

        List<Integer> images = getNatureImages(ctx);
        List<String> titles = getStringsShort(ctx);
        List<String> subtitles = getStringsShort(ctx);

        for (int i = 0; i < count; i++) {
            CardViewImg obj = new CardViewImg();
            obj.image = images.get(getRandomIndex(images.size()));
            obj.title = titles.get(getRandomIndex(titles.size()));
            obj.subtitle = subtitles.get(getRandomIndex(subtitles.size()));
            items.add(obj);
        }
        return items;
    }

    /**
     * Generate dummy data people
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<People> getPeopleData(Context ctx) {
        List<People> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);

        for (int i = 0; i < drw_arr.length(); i++) {
            People obj = new People();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.email = Tools.getEmailFromName(obj.name);
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }
    /**
     * Generate dummy data people
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<BookingType> getBookingTypes(Context ctx) {
        List<BookingType> items = new ArrayList<>();
        BookingType getalyft = new BookingType("Get-a-Lyft",true);
        BookingType Flightbookings = new BookingType("Flight Bookings",true);
        BookingType HireAVehicle = new BookingType("Vehicle Bookings",true);
        BookingType Trainbookings = new BookingType("Train Bookings",true);
        BookingType HotelBookings = new BookingType("Hotel Bookings",true);
        BookingType bustickets = new BookingType("Bus tickets",true);
        BookingType cruisetickets = new BookingType("Curise bookings",true);
        BookingType meteredtaxi = new BookingType("Metered taxi bookings",true);

       // TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        //String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);

//        for (int i = 0; i < drw_arr.length(); i++) {
//            People obj = new People();
//            obj.image = drw_arr.getResourceId(i, -1);
//            obj.name = name_arr[i];
//            obj.email = Tools.getEmailFromName(obj.name);
//            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
//            items.add(obj);
//        }
       // Collections.shuffle(items);

        items.add(getalyft);
        items.add(Flightbookings);
        items.add(HireAVehicle);
        items.add(Trainbookings);
        items.add(HotelBookings);
        items.add(bustickets);
        items.add(cruisetickets);
        items.add(meteredtaxi);
        return items;
    }

    /**
     * Generate dummy data social
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<Social> getSocialData(Context ctx) {
        List<Social> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.social_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.social_names);

        for (int i = 0; i < drw_arr.length(); i++) {
            Social obj = new Social();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    /**
     * Generate dummy data inbox
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<Inbox> getInboxData(Context ctx) {
        List<Inbox> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
        String date_arr[] = ctx.getResources().getStringArray(R.array.general_date);

        for (int i = 0; i < drw_arr.length(); i++) {
            Inbox obj = new Inbox();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.from = name_arr[i];
            obj.email = Tools.getEmailFromName(obj.from);
            obj.message = ctx.getResources().getString(R.string.lorem_ipsum);
            obj.date = date_arr[randInt(date_arr.length - 1)];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    /**
     * Generate dummy data image
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<Image> getImageDate(Context ctx) {
        List<Image> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.sample_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.sample_images_name);
        String date_arr[] = ctx.getResources().getStringArray(R.array.general_date);
        for (int i = 0; i < drw_arr.length(); i++) {
            Image obj = new Image();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.brief = date_arr[randInt(date_arr.length - 1)];
            obj.counter = r.nextBoolean() ? randInt(500) : null;
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    /**
     * Generate dummy data shopping category
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<ShopCategory> getShoppingCategory(Context ctx) {
        List<ShopCategory> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_category_icon);
        TypedArray drw_arr_bg = ctx.getResources().obtainTypedArray(R.array.shop_category_bg);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_category_title);
        String brief_arr[] = ctx.getResources().getStringArray(R.array.shop_category_brief);
        for (int i = 0; i < drw_arr.length(); i++) {
            ShopCategory obj = new ShopCategory();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.image_bg = drw_arr_bg.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.brief = brief_arr[i];
            obj.imageDrw = AppCompatResources.getDrawable(ctx, obj.image);
            items.add(obj);
        }
        return items;
    }

    /**
     * Generate dummy data shopping product
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<ShopProduct> getShoppingProduct(Context ctx) {
        List<ShopProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_image);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_title);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price);
        for (int i = 0; i < drw_arr.length(); i++) {
            ShopProduct obj = new ShopProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }


    /**
     * Generate dummy data music song
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<MusicSong> getMusicSong(Context ctx) {
        List<MusicSong> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.album_cover);
        String song_name[] = ctx.getResources().getStringArray(R.array.song_name);
        String album_name[] = ctx.getResources().getStringArray(R.array.album_name);
        for (int i = 0; i < drw_arr.length(); i++) {
            MusicSong obj = new MusicSong();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = song_name[i];
            obj.brief = album_name[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    /**
     * Generate dummy data music album
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<MusicAlbum> getMusicAlbum(Context ctx) {
        List<MusicAlbum> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.album_cover);
        String album_name[] = ctx.getResources().getStringArray(R.array.album_name);
        for (int i = 0; i < drw_arr.length(); i++) {
            MusicAlbum obj = new MusicAlbum();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = album_name[i];
            obj.brief = getRandomIndex(15) + " MusicSong (s)";
            obj.color = MaterialColor.getColor(ctx, obj.name, i);
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }

    public static String formatTime(long time) {
        // income time
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        // current time
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat dateFormat = null;
        if (date.get(Calendar.YEAR) == curDate.get(Calendar.YEAR)) {
            if (date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR)) {
                dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            } else {
                dateFormat = new SimpleDateFormat("MMM d", Locale.US);
            }
        } else {
            dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        }
        return dateFormat.format(time);
    }

    private static int getRandomIndex(int max) {
        return r.nextInt(max - 1);
    }

    public static List<BookingType> getFAQCategoryData(Context ctx) {
        List<BookingType> items = new ArrayList<>();
        BookingType getalyft = new BookingType("Get-a-Lyft",true);
        BookingType Flightbookings = new BookingType("Flight Bookings",true);
        BookingType HireAVehicle = new BookingType("Vehicle Bookings",true);
        BookingType Trainbookings = new BookingType("Train Bookings",true);
        BookingType HotelBookings = new BookingType("Hotel Bookings",true);
        BookingType bustickets = new BookingType("Bus tickets",true);
        BookingType cruisetickets = new BookingType("Curise bookings",true);
        BookingType meteredtaxi = new BookingType("Metered taxi bookings",true);

        // TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        //String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);

//        for (int i = 0; i < drw_arr.length(); i++) {
//            People obj = new People();
//            obj.image = drw_arr.getResourceId(i, -1);
//            obj.name = name_arr[i];
//            obj.email = Tools.getEmailFromName(obj.name);
//            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
//            items.add(obj);
//        }
        // Collections.shuffle(items);

        items.add(getalyft);
        items.add(Flightbookings);
        items.add(HireAVehicle);
        items.add(Trainbookings);
        items.add(HotelBookings);
        items.add(bustickets);
        items.add(cruisetickets);
        items.add(meteredtaxi);
        return items;
    }

    public static ArrayList<TravelModel> getAvailableServices(){
        ArrayList<TravelModel> servicesList =  new ArrayList<>();
        //TravelModel travelModel = new TravelModel();
        //travelModel.setName(c.getString("srcName") + "-" + c.getString("destName"));
        // travelModel.setSubTitle("Arrival - " + c.getString("ArrivalTime") + "\nDeparture - " + c.getString("DepartureTime"));
        //travelModel.setPrice("Amount - " + c.getString("Amount") + "$");

        // travelModel.setActive(Integer.parseInt(c.getString("Active")));

        for (int i = 0; i < 5; i++) {
            TravelModel obj = new TravelModel();
            obj.setName("travels"+i);
            obj.setPrice("200"+(i*50));
            obj.setTime(DateTime.now().toString());
            obj.setSubTitle("good morning travels"+1);
            servicesList.add(obj);
        }
        //Collections.shuffle(items);

        return servicesList;

    }
}
