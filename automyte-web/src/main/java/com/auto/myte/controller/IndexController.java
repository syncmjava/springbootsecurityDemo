package com.auto.myte.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.auto.myte.common.message.SpringMessageResourceMessages;
import com.auto.myte.config.PropertiesConfig;
import com.auto.myte.entity.ReceiptInfo;
import com.auto.myte.service.ReceiptInfoService;
import com.auto.myte.utils.CommonUtils;

@Controller
@RequestMapping("/")
public class IndexController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);
 
	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private SpringMessageResourceMessages messageSource;

	@Autowired
	private ReceiptInfoService service;

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/")
	public String home(Model model) throws Exception {
		return "uploadfile";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/admin")
	public String admin(Model model) throws Exception {
		return "admin";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) throws Exception {
		return "list";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String index(@RequestParam(value = "id", required = false) String id, Model model) throws Exception {
		logger.info("id = " + id);
		model.addAttribute("id", id);
		return "detail";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/uploadfile")
	public String uploadFile() throws Exception {
		return "uploadfile";
	}

	/**
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("myFile") MultipartFile file, Model model) throws Exception {
    	if ( !file.isEmpty() ) {
            String fileName = file.getOriginalFilename();
            if (CommonUtils.isImage(fileName)) {
            	//Now do something with file...
                FileCopyUtils.copy(file.getBytes(), new File(propertiesConfig.getFilepath() + fileName));
                
                // call ocr api
                ReceiptInfo receipt = new ReceiptInfo();
                // category
                receipt.setCategory_id("");
                // 金額
                receipt.setAmount("");
                // 日付
                receipt.setDate("");
                // 登録者
                receipt.setEid("");
                // Receipt Name
                receipt.setName("");
                // Image URL
                receipt.setImage_name(propertiesConfig.getImageUrl() + fileName);
                int insertFlag = service.insertReceiptInfo(receipt);
                if (insertFlag > 0) {
                	model.addAttribute("messageImage", "upload is ok.");
                } else {
                	model.addAttribute("messageImage", "upload is ng.");
                	
                }
            }else {
            	model.addAttribute("messageImage", "please upload image file.");
            }
        } 
        return "uploadfile";
    }

//	/**
//     * @param form
//     * @param result
//     * @return
//     */
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public String upload(@RequestParam("myFile") MultipartFile file, Model model) throws Exception {
//    	if ( !file.isEmpty() ) {
//            String fileName = file.getOriginalFilename();
//            if (CommonUtils.isCsv(fileName)) {
//                try {
//                    BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
//                    String readLine = null;
//                    List<CrawlerProductJob> records = new ArrayList<CrawlerProductJob>();
//                    while((readLine = br.readLine()) != null) {
//                    	CrawlerProductJob job = new CrawlerProductJob();
//                    	job.setCrawler_product_no(readLine.trim());
//                    	records.add(job);
//                    }
//                    productSearchService.importCsvFileToDB(records);
//                	model.addAttribute("messageInfo", "success !! import to Db count : " + records.size());
//                    
//                }
//                 catch ( Exception e ) {
//                  	model.addAttribute("message", "please upload correct csv file.");
//                 }
//            }else {
//            	model.addAttribute("message", "please upload csv file.");
//            }
//        } 
//        return "uploadfile";
//    }
//
//	/**
//	 * Handle request to download an Excel document
//	 */
//	@RequestMapping(value = "/download", method = RequestMethod.GET)
//	public void download(@RequestParam(value = "pno", required = false) String pno, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		try {
//			File file = ResourceUtils.getFile("classpath:template.xlsx");
//			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
//			Sheet sheet = wb.getSheetAt(0);
//			List<ReceiptInfo> productList = service.getProductAllPricesByPno(pno);
//			sheet.getRow(3).getCell(1).setCellValue(pno);
//			for (ReceiptInfo productBaseInfo : productList) {
//				// 1 yamada
//				// 2 rakuten
//				// 3 Amazon
//				// 4 yodobasi
//				double f = Float.parseFloat(productBaseInfo.getProduct_price());
//				if ("1".equals(productBaseInfo.getShop_id())) {
//					sheet.getRow(3).getCell(3).setCellValue(f);
//				} else if ("2".equals(productBaseInfo.getShop_id())) {
//					sheet.getRow(4).getCell(3).setCellValue(f);
//				} else if ("3".equals(productBaseInfo.getShop_id())) {
//					sheet.getRow(5).getCell(3).setCellValue(f);
//				} else if ("4".equals(productBaseInfo.getShop_id())) {
//					sheet.getRow(6).getCell(3).setCellValue(f);
//				}
//			}
//			sheet.getRow(7).getCell(1).setCellValue("実施日：" + CommonUtils.getCurrentDate());
//			
//			wb.write(os);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		byte[] content = os.toByteArray();
//		InputStream is = new ByteArrayInputStream(content);
//
//		response.reset();
//		response.setContentType("application/vnd.ms-excel;charset=utf-8");
//		response.setHeader("Content-Disposition",
//				"attachment;filename=" + new String((pno + ".xlsx").getBytes(), "iso-8859-1"));
//
//		ServletOutputStream out = response.getOutputStream();
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//		try {
//			bis = new BufferedInputStream(is);
//			bos = new BufferedOutputStream(out);
//			byte[] buff = new byte[2048];
//			int bytesRead;
//			// Simple read/write loop.
//			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//				bos.write(buff, 0, bytesRead);
//			}
//		} catch (final IOException e) {
//			throw e;
//		} finally {
//			if (bis != null)
//				bis.close();
//			if (bos != null)
//				bos.close();
//		}
//	}

}
