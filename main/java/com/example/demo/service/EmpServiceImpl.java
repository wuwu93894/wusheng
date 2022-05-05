package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.EmpData;
import com.example.demo.form.EmpForm;
import com.example.demo.mapper.EmpMapper;
@Service
public class EmpServiceImpl implements EmpService{
	//注入 empMapper
	@Autowired
	private EmpMapper empMapper;
	@Override
	public ArrayList<EmpData> listEmp() {
		//empMapper中のlistEmp方法 リスポンスを得る
		return  empMapper.listEmp();
	}
	@Override
	public List<EmpData> searchEmp(String keyWord){
		//empMapperなかのsearchEmp方法 リスポンスを得る
		return  empMapper.searchEmp("%"+keyWord+"%");	
	}
	@Override
	public EmpData getEmpData(String empCd) {
		//empMapper中getEmpData方法 リスポンスを得る
	return  empMapper.getEmpData(empCd);
	}
	@Override
	public void insertEmp(EmpForm empForm) {
		empMapper.insertEmp(empForm);
	}
	@Override
	public void changeEmp(EmpForm empForm) {
		empMapper.changeEmp(empForm);
		
	}
	@Override
	public void deleteEmp (String empCd) {
		empMapper.deleteEmp(empCd);
	}
	
	// 社員情報をexcelファイルにダウンロード
		public void empListExcel(HttpServletResponse response, List<EmpData> empList) throws IOException {
			if (empList != null) {
				// ワークブックを作成する
				HSSFWorkbook workBook = new HSSFWorkbook();

				// ワークシートを作成する
				HSSFSheet sheet = workBook.createSheet("社員情報一覧表");
				HSSFRow row = null;

				// excelの一行目、テーブルのタイトルを作成する
				row = sheet.createRow(0);

				// 行の高さを設定する
				row.setHeight((short) (22.50 * 20));
				// 行のセルを作成
				row.createCell(1).setCellValue("社員情報一覧表");

				// excelの二行目、テーブルのヘーダを作成する
				row = sheet.createRow(1);
				// 高さを設定する
				row.setHeight((short) (22.50 * 20));

				row.createCell(0).setCellValue("社員番号");
				row.createCell(1).setCellValue("名前");
				row.createCell(2).setCellValue("生年月日");
				row.createCell(3).setCellValue("国籍");
				row.createCell(4).setCellValue("性別");

				// 社員情報リス内容を読み込み
				for (int i = 0; i < empList.size(); i++) {
					// excelの三行目から書き込む
					row = sheet.createRow(i + 2);
					// 社員情報リストを一つずつ取り出す
					EmpData empData = empList.get(i);
					// 社員番号の設定
					row.createCell(0).setCellValue(empData.getEmpCd());
					// 社員名前の設定
					row.createCell(1).setCellValue(empData.getName());
					// 生年月日の設定
					row.createCell(2).setCellValue(empData.getBirthday().toString());
					// 国籍の設定
					row.createCell(3).setCellValue(empData.getNationality().getNationalityName());
					// 性別の設定
					row.createCell(4).setCellValue(empData.getGender().getGenderName());
				}
				// 行のデフォルトの高さを設定
				sheet.setDefaultRowHeight((short) (16.50 * 20));
				// 列の幅を設定
				for (int i = 0; i < 15; i++) {
					sheet.setColumnWidth(i, 255 * 15);
				}
				// レスポンスのコンテンツタイプの設定
				response.setContentType("application/vnd.ms-excel;charset=utf-8");

				// レスポンスにバイナリデータを書き込むのに適したServletOutputStreamを返す
				OutputStream outStream = response.getOutputStream();

				// attachment (ダウンロードすべきであることを示す
				// 多くのブラウザーは filename 引数の値を使い、「名前を付けて保存」ダイアログを表示します) を最初の引数して指定
				String exportValue = "attachment; filename=" + "empList" + ".xls";

				// レスポンスのヘーダを設定、ダウンロードしてローカルに保存する添付ファイルとするかを示す
				response.setHeader("Content-disposition", exportValue);// Excel名

				// このワークブックをOutputStreamに書き出す
				workBook.write(outStream);
				// ワークブックを閉じる
				workBook.close();

				// 出力ストリームをフラッシュして、バッファリングされていたすべての出力バイトを強制的に書き込みます
				outStream.flush();
				// 出力ストリームを閉じる
				outStream.close();
			}
		}
}
