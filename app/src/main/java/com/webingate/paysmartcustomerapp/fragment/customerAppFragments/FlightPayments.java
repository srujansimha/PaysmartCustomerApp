package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.resource.bytes.BytesResource;
import com.google.gson.JsonObject;
import com.pchmn.materialchips.R2;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.DialogFlightPaymentTransactionsFragment;
import com.webingate.paysmartcustomerapp.activity.customerapp.DialogPaymentTransactionsFragment;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.BusLayout;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerPayResponse;
import com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog;
import com.webingate.paysmartcustomerapp.customerapp.Mytickets;
import com.webingate.paysmartcustomerapp.customerapp.Stops;
import com.webingate.paysmartcustomerapp.customerapp.Ticket_Source_Destination_Date;
import com.webingate.paysmartcustomerapp.customerapp.TravelModel;
import com.webingate.paysmartcustomerapp.customerapp.Travels;



import org.joda.time.DateTime;
import org.w3c.dom.Document;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NewApi")
public class FlightPayments extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String ID = "idKey";
    Double totalamount=0.0;
    private static final String ARG_SECTION_NUMBER = "section_number";
    Button bookTicket, myTickets, eWallet;
    @BindView(R.id.btn_ewallet)
    AppCompatButton ewallet;
    @BindView(R.id.btn_preferences)
    AppCompatButton btnPreferences;
    @BindView(R.id.btn_feedback_enquiry)
    AppCompatButton btnFeedbackEnquiry;

    Unbinder unbinder;
    private String response;

    String PassengerId;
    Toast toast;
    ProgressDialog dialog ;
    public static FlightPayments newInstance(int SectionNumber) {
        FlightPayments home = new FlightPayments();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, SectionNumber);
        home.setArguments(args);
        return home;
    }

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.flightpaymentmethods, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ApplicationConstants.userid=prefs.getInt(ID,0);

        dialog =  new ProgressDialog.Builder(getActivity())
                .setTitle("Loading...")
                .setTitleColorRes(R.color.gray)
                .build();
        unbinder = ButterKnife.bind(this, v);

        ewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationConstants.pmode="E-Wallet";
                ArrayList<CustomerFlightResponce> pasengerlist=new ArrayList<>();
                Double famt=Double.parseDouble(ApplicationConstants.FlightAmount);

                for(int i=0;i<ApplicationConstants.passengerlist.size();i++){
                    CustomerFlightResponce obj=new CustomerFlightResponce();
                    obj.setName(ApplicationConstants.passengerlist.get(i));
                    obj.setAge(Integer.parseInt((ApplicationConstants.passengerage.get(i)!=null)?(ApplicationConstants.passengerage.get(i)):"0"));
                    obj.setappuserid(ApplicationConstants.userid);
                    obj.setFlag("I");
                    obj.setgender((ApplicationConstants.passengergender.get(i)));
                    obj.setMobileno(ApplicationConstants.PassengerMobileno);
                    obj.setEmailid(ApplicationConstants.PassengerEmailid);
                    obj.setSource(ApplicationConstants.fsource);
                    obj.setDestination(ApplicationConstants.fdestination);
                    obj.setSeatno(String.valueOf(ApplicationConstants.seatsSelected.get(i)));
                    totalamount=totalamount+famt;
                    pasengerlist.add(obj);
                    obj=null;
                }

                if(pasengerlist.size()!=0){
                    SaveFlightPassengerDetails(pasengerlist);
                }
            }
        });
        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationConstants.pmode="Net Banking";
                ArrayList<CustomerFlightResponce> pasengerlist=new ArrayList<>();
                Double famt=Double.parseDouble(ApplicationConstants.FlightAmount);

                for(int i=0;i<ApplicationConstants.passengerlist.size();i++){
                    CustomerFlightResponce obj=new CustomerFlightResponce();
                    obj.setName(ApplicationConstants.passengerlist.get(i));
                    obj.setAge(Integer.parseInt((ApplicationConstants.passengerage.get(i)!=null)?(ApplicationConstants.passengerage.get(i)):"0"));
                    obj.setappuserid(ApplicationConstants.userid);
                    obj.setFlag("I");
                    obj.setgender((ApplicationConstants.passengergender.get(i)));
                    obj.setMobileno(ApplicationConstants.PassengerMobileno);
                    obj.setEmailid(ApplicationConstants.PassengerEmailid);
                    obj.setSource(ApplicationConstants.fsource);
                    obj.setDestination(ApplicationConstants.fdestination);
                    obj.setSeatno(String.valueOf(ApplicationConstants.seatsSelected.get(i)));
                    totalamount=totalamount+famt;
                    pasengerlist.add(obj);
                    obj=null;
                }

                if(pasengerlist.size()!=0){
                    SaveFlightPassengerDetails(pasengerlist);
                }
            }
        });
        btnFeedbackEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationConstants.pmode="Credit & Debit";

                //ApplicationConstants.pmode="Net Banking";
//                JsonObject object = new JsonObject();
//                object.addProperty("Transactionid", "1256");
//                object.addProperty("Transaction_Number", "ts1258967");
//                object.addProperty("Amount", "150");
//                object.addProperty("Paymentmode", "1");
//                object.addProperty("TransactionStatus", "1");
//                object.addProperty("Gateway_transId", "wb123");
//                object.addProperty("flag","I");
//                Pay(object);
                ArrayList<CustomerFlightResponce> pasengerlist=new ArrayList<>();
                Double famt=Double.parseDouble(ApplicationConstants.FlightAmount);

                for(int i=0;i<ApplicationConstants.passengerlist.size();i++){
                 CustomerFlightResponce obj=new CustomerFlightResponce();
                obj.setName(ApplicationConstants.passengerlist.get(i));
                obj.setAge(Integer.parseInt((ApplicationConstants.passengerage.get(i)!=null)?(ApplicationConstants.passengerage.get(i)):"0"));
                obj.setappuserid(ApplicationConstants.userid);
                obj.setFlag("I");
                obj.setgender((ApplicationConstants.passengergender.get(i)));
                obj.setMobileno(ApplicationConstants.PassengerMobileno);
                obj.setEmailid(ApplicationConstants.PassengerEmailid);
                obj.setSource(ApplicationConstants.fsource);
                obj.setDestination(ApplicationConstants.fdestination);
                obj.setSeatno(String.valueOf(ApplicationConstants.seatsSelected.get(i)));
                    totalamount=totalamount+famt;
                pasengerlist.add(obj);
                    obj=null;
                }

                if(pasengerlist.size()!=0){
                    SaveFlightPassengerDetails(pasengerlist);
                }
                /*PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.execute();*/
            }
        });
        return v;
    }

    private void goPage(int page) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (page) {
            case ApplicationConstants.HOME:
                fragmentClass = FlightPayments.class;
                break;
            case ApplicationConstants.TICKET_SOURCE_DESTINATION:
                fragmentClass = Ticket_Source_Destination_Date.class;
                break;
            case ApplicationConstants.TICKETS:
                fragmentClass = Mytickets.class;
                break;
            case ApplicationConstants.STOPS:
                fragmentClass = Stops.class;
                break;
            case ApplicationConstants.TRAVELS:
                fragmentClass = Travels.class;
                break;
            case ApplicationConstants.BUSLAYOUT:
                fragmentClass = BusLayout.class;
                break;
            case ApplicationConstants.EWALLET:
                fragmentClass = BusLayout.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       /* fragmentTransaction.setCustomAnimations(
                R.anim.rotate_forward,
                R.anim.rotate_backward);*/
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showDialogPaymentTransactions() {
        //ApplicationConstants.transactionsId=id;
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();

        DialogFlightPaymentTransactionsFragment dptf = new DialogFlightPaymentTransactionsFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content, dptf).addToBackStack(null).commit();

    }



//    public void byteArrayToFile(byte[] bArray) {
//        try {
//            Document myDocument = new Document(PageSize.LETTER);
//            PdfWriter.GetInstance(myDocument, new FileStream("mydocument.pdf", FileMode.Create));
//            myDocument.Open();
//            myDocument.Add(new Paragraph(Xml.Encoding.UTF8.GetString(bytes)));
//            myDocument.Close()
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//        }
//    }
    public void SaveFlightPassengerDetails(ArrayList<CustomerFlightResponce> jsonObject){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .savepassenger(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerFlightResponce>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            //byteArrayToFile();
                            Log.d("OnError ", e.getMessage());
                            //DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerFlightResponce> responselist) {
                        List<CustomerFlightResponce> res=responselist;
                         PassengerId = res.get(0).getPasssengerId();
                        Log.e("PDF Bytes", res.get(0).getDescription());
//                        byte[] bArray = (res.get(0).getDescription()).getBytes();
//                        File someFile = new File("c:/Java/Output_File.pdf");
//                        try {
//                            FileOutputStream fos = new FileOutputStream(someFile);
//                            fos.write(bArray);
//                            fos.flush();
//                            fos.close();
//                        } catch (Exception e) {
//                        }
//                        try {
//
//
//                            File file = new File("java.pdf");
//
//                            FileInputStream fis = new FileInputStream(file);
//                            //System.out.println(file.exists() + "!!");
//                            //InputStream in = resource.openStream();
//                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                            byte[] buf = new byte[Integer.parseInt(res.get(0).getDescription())];
//                            try {
//                                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
//                                    bos.write(buf, 0, readNum); //no doubt here is 0
//                                    //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
//                                    System.out.println("read " + readNum + " bytes,");
//                                }
//                            } catch (IOException ex) {
//
//                            }
//                            byte[] bytes = bos.toByteArray();
//
//                            //below is the different part
//                            File someFile = new File("java2.pdf");
//                            FileOutputStream fos = new FileOutputStream(someFile);
//                            fos.write(bytes);
//                            fos.flush();
//                            fos.close();
//                        }
//                        catch(IOException ex){
//                        }

                       // createPDF();
                        JsonObject object = new JsonObject();
                        object.addProperty("Amount",totalamount);
                        object.addProperty("StatusId",47);
                        object.addProperty("flag","I");
                        object.addProperty("HolderName",ApplicationConstants.username);
                        object.addProperty("TransModeId",25);
                        object.addProperty("TotalAmount",totalamount);
                        object.addProperty("PassengerId",PassengerId);
                        saveFBTransactionMaster(object);
                    }
                });
    }

    public void saveFBTransactionMaster(JsonObject object){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .saveFBTransactionMaster(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerFlightResponce>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerFlightResponce> responselist) {
                        List<CustomerFlightResponce> res=responselist;



                        JsonObject object = new JsonObject();
                        object.addProperty("Transactionid", "1256");
                        object.addProperty("Transaction_Number", "ts1258967");
                        object.addProperty("Amount", "150");
                        object.addProperty("Paymentmode", "1");
                        object.addProperty("TransactionStatus", "1");
                        object.addProperty("Gateway_transId", "wb123");
                        object.addProperty("flag","I");
                        Pay(object);
                    }
                });
    }
    public void Pay(JsonObject jsonObject){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .Pay(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerPayResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerPayResponse> responselist) {
                        List<CustomerPayResponse> res=responselist;
                        ApplicationConstants.pdate=res.get(0).getPaymentDate();
                        ApplicationConstants.ptime=res.get(0).getPaymentTime();

                        showDialogPaymentTransactions();

                    }
                });
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast=Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
    public void StartDialogue(){

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void StopDialogue(){
        if(dialog.isShowing()){
            dialog.cancel();
        }

    }

//    private void createPDF (){
//
//        Document doc = new Document();
//        PdfWriter docWriter = null;
//
//        DecimalFormat df = new DecimalFormat("0.00");
//
//        try {
//
//            //special font sizes
//            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
//            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//            //file path
//            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
//            File file = new File(directory_path);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            String path = directory_path+"test-2.pdf";
//            //String path = "docs/" + pdfFilename;
//            docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
//
//            //document header attributes
//            doc.addAuthor("betterThanZero");
//            doc.addCreationDate();
//            doc.addProducer();
//            doc.addCreator("MySampleCode.com");
//            doc.addTitle("Report with Column Headings");
//            doc.setPageSize(PageSize.LETTER);
//
//            //open document
//            doc.open();
//
//            //create a paragraph
////            Paragraph paragraph = new Paragraph("iText ® is a library that allows you to create and " +
////                    "manipulate PDF documents. It enables developers looking to enhance web and other " +
////                    "applications with dynamic PDF document generation and/or manipulation.");
//
//            float[] columnWidths1 = {5f,5f };
//            PdfPTable table1=new PdfPTable(2);
//            //table1.setWidthPercentage(90f);
//            PdfPCell cell1=new PdfPCell(new Phrase("Air india Airlines Tickets",bfBold12));
//            cell1.setBorder(0);
//            cell1.setColspan(2);
//            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table1.addCell(cell1);
//
//            PdfPCell blankcell=new PdfPCell(new Phrase(Chunk.NEWLINE));
//            blankcell.setColspan(2);
//            blankcell.setBorder(0);
//            table1.addCell(blankcell);
//
//            PdfPCell Address=new PdfPCell(new Phrase("AIR INDIA SMART TICKETS"));
//            Address.setColspan(2);
//            Address.setBorder(0);
//            Address.setHorizontalAlignment(Element.ALIGN_LEFT);
//            table1.addCell(blankcell);
//
//            PdfPCell name=new PdfPCell(new Phrase("Your Boarding Pass"));
//            name.setColspan(2);
//            name.setBorder(0);
//
//            name.setHorizontalAlignment(Element.ALIGN_LEFT);
//            table1.addCell(name);
//
//
//            //specify column widths
//            float[] columnWidths = {1.5f, 2f, 5f, 2f};
//            //create PDF table with the given widths
//            PdfPTable table = new PdfPTable(columnWidths);
//            // set table width a percentage of the page width
//            table.setWidthPercentage(90f);
//
//            //insert column headings
//            insertCell(table, "Order No", Element.ALIGN_RIGHT, 1, bfBold12);
//            insertCell(table, "Account No", Element.ALIGN_LEFT, 1, bfBold12);
//            insertCell(table, "Account Name", Element.ALIGN_LEFT, 1, bfBold12);
//            insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
//            table.setHeaderRows(1);
//
//            //insert an empty row
//            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
//            //create section heading by cell merging
//            insertCell(table, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
//            double orderTotal, total = 0;
//
//            //just some random data to fill
//            for(int x=1; x<5; x++){
//
//                insertCell(table, "10010" + x, Element.ALIGN_RIGHT, 1, bf12);
//                insertCell(table, "ABC00" + x, Element.ALIGN_LEFT, 1, bf12);
//                insertCell(table, "This is Customer Number ABC00" + x, Element.ALIGN_LEFT, 1, bf12);
//
//                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
//                total = total + orderTotal;
//                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);
//
//            }
//            //merge the cells to create a footer for that section
//            insertCell(table, "New York Total...", Element.ALIGN_RIGHT, 3, bfBold12);
//            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);
//
//            //repeat the same as above to display another location
//            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
//            insertCell(table, "California Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
//            orderTotal = 0;
//
//            for(int x=1; x<7; x++){
//
//                insertCell(table, "20020" + x, Element.ALIGN_RIGHT, 1, bf12);
//                insertCell(table, "XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);
//                insertCell(table, "This is Customer Number XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);
//
//                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
//                total = total + orderTotal;
//                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);
//
//            }
//            insertCell(table, "California Total...", Element.ALIGN_RIGHT, 3, bfBold12);
//            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);
//
//            //add the PDF table to the paragraph
//            //paragraph.add(table);
//            // add the paragraph to the document
//           doc.add(table1);
//
//        }
//        catch (DocumentException dex)
//        {
//            dex.printStackTrace();
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        finally
//        {
//            if (doc != null){
//                //close the document
//                doc.close();
//            }
//            if (docWriter != null){
//                //close the writer
//                docWriter.close();
//            }
//            viewPdf("test-2.pdf", "mypdf");
//        }
//    }
//    public void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {
//        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
//        cell.setHorizontalAlignment(align);
//        if (text.trim().equalsIgnoreCase("")) {
//            cell.setMinimumHeight(10f);
//        }
//        table.addCell(cell);
//    }
    private void viewPdf(String file, String directory) {

        File file1 = new File("/storage/emulated/0/mypdf/test-2.pdf");
        // File file = new
        // File(Environment.getExternalStorageDirectory()+"/pdf/PDFOpenParameters.pdf");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file1);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);

//        File file1 = new File("/storage/emulated/0/mypdf/test-2.pdf");
//        Uri path = Uri.fromFile(file1);
//        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        pdfOpenintent.setDataAndType(path, "application/pdf");
//        try {
//            startActivity(pdfOpenintent);
//        }
//        catch (ActivityNotFoundException e) {
//
//        }

//        File pdfFile = new File(Environment.getExternalStorageDirectory().getPath() + "/mypdf/" + file);
//        Uri path = Uri.fromFile(pdfFile);
//
//        // Setting the intent for pdf reader
//        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//        pdfIntent.setDataAndType(path, "application/pdf");
//        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        try {
//            startActivity(pdfIntent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getContext(), "Can't read pdf file", Toast.LENGTH_SHORT).show();
//        }
    }

}
