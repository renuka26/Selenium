
package API;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import MasterSetup.Temp;
import webdriver.Webdriver;


public class EmailConfiguration {
	
public static void sendReport() {
		
		try{
			
			
			ReportConfigurations.appendTestCaseSubTitle("Send Automation Report In Email");
			
			
			final String userName_sl=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, Temp.SheetsConfigurations.emailConfigurationSheet, 1, 1);
			final String password_sl=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, Temp.SheetsConfigurations.emailConfigurationSheet, 2, 1);
			String Environment_sl=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, Temp.SheetsConfigurations.emailConfigurationSheet, 1, 4);
			
			String recipientsToList_sl=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, Temp.SheetsConfigurations.emailConfigurationSheet, 1, 2);
			String[] recipientsToList_strArr=recipientsToList_sl.split(",");int counter=0;
			InternetAddress[] reciepientToAddress_strArr=new InternetAddress[recipientsToList_strArr.length];
			
			for(String recipient:recipientsToList_strArr)
				reciepientToAddress_strArr[counter++]=new InternetAddress(recipient.trim());
			
			String recipientsCCList_sl=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, Temp.SheetsConfigurations.emailConfigurationSheet, 1, 3);
			String[] recipientsCCList_strArr=recipientsCCList_sl.split(",");int counter1=0;
			InternetAddress[] reciepientCCAddress_strArr=new InternetAddress[recipientsCCList_strArr.length];
			
			for(String recipient:recipientsCCList_strArr)
				reciepientCCAddress_strArr[counter1++]=new InternetAddress(recipient.trim());
			
//			Properties props = new Properties();//"smtp.gmail.com"  smtp-mail.outlook.com
//			props.put("mail.smtp.host", "smtp.gmail.com");
//			props.put("mail.smtp.socketFactory.port", "465");//465   587
//			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.auth", "true");

			Properties props = new Properties();//"smtp.gmail.com"  
			props.put("mail.smtp.host", "smtp-mail.outlook.com");
			props.put("mail.smtp.socketFactory.port", "587");//465   587
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName_sl, password_sl);
						}
			});
			
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName_sl));
			message.addRecipients(Message.RecipientType.TO, reciepientToAddress_strArr);
			message.addRecipients(Message.RecipientType.CC, reciepientCCAddress_strArr);
			String msg=
					"<p style='font-family:Calibri; font-size: 15px;'><h3>Hi All ,"
					+"<p style='font-family:Calibri; font-size: 15px;'><h3><span style='color:blue'> "+Webdriver.projectName+"</span> has been executed sucessfully in "+Environment_sl+" Environment .Execution Details has been mentioned below .</br></h3>"
					+"<p style='font-family:Calibri; font-size: 15px;'><h3><b>Environment   		:</b>"+Webdriver.url+"</br>"
					+"<p style='font-family:Calibri; font-size: 15px;'><h3><b>Browser   	:</b>"+Webdriver.browsername+"</br>"
					+"<p style='font-family:Calibri; font-size: 15px;'><h3><b>Operating System   	:</b>"+System.getProperty("os.name")+"</br>"
					+"<p style='font-family:Calibri; font-size: 15px;'><h3><b>Total Execution Time    :</b>"+Webdriver.totalTime+"</br>"		
					+"<p style='font-family:Calibri; font-size: 15px;'><h3>Please find the attachment for the Automation Report.</p>";
			
			message.setSubject(Webdriver.projectName+" Automation Report");
			
			/*
			 * "<pre><h3>Hi All ,</h3>"
					+"<h3><p style='font-family:Centaur; font-size: 15px;'><span style='color:blue'> "+HbxWebdriver.projectName+"</span> has been executed sucessfully in "+Environment_sl+" Enviroinment .Execution Details has been mentioned below .</br></h3>"
					+"<pre><br><b><h3>Enviroinment   	:</b>"+HbxWebdriver.url+"</br>"
					+"<pre><br><b><h3>Build Number   	:</b>"+Temp.buildNumber_sg+"</br>"
					+"<pre><br><b><h3>Execution Time    :</b>"+HbxWebdriver.totalTime+"</br>"		
					+"<pre><br>Please find the attachment for the Automation Report.</h3></p></pre>";
			
			 */
		    Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource(Webdriver.testlogFolder.getAbsolutePath().concat("\\").concat(Webdriver.testLogHTMLFolderName)+"\\"+Webdriver.projectName+".html");
			
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(source.getName());
			
			
			multipart.addBodyPart(messageBodyPart2);
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(msg, "text/html");
			multipart.addBodyPart(messageBodyPart1);
			message.setContent(multipart);
			Transport.send(message);
			
			ReportConfigurations.appendPass("Report has been sucessfully sent to  :"+recipientsToList_sl);
			ReportConfigurations.appendPass("Reciepients CC:"+recipientsCCList_sl);
			
			System.out.println("=====Email Sent=====");
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
