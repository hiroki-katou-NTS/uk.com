using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// タスク実行前* エラー情報
    /// </summary>
    public class StartingErrorInfo
    {
        public StartingErrorInfo() { }

        #region CONST
        /// <summary>
        /// 情報ファイル
        /// </summary>
        private const string XML_FILE = "error.xml";
        #endregion CONST

        #region メンバー変数
        /// <summary>
        /// 契約情報
        /// </summary>
        public Contract Contract = new Contract();

        /// <summary>
        /// エラー内容
        /// </summary>
        public string ErrorMsg = string.Empty;
        #endregion メンバー変数

        /// <summary>
        /// エラー情報を生成する
        /// </summary>
        /// <param name="contractCode">契約コード</param>
        /// <param name="serverPath">サーバーの物理パス</param>
        /// <returns>タスク情報</returns>
        public static StartingErrorInfo Create(string contractCode, string serverPath)
        {
            #region エラー情報を生成する
            try
            {
                // 契約フォルダ
                string contractFolder = Contract.CreateContractFolder(contractCode, serverPath);

                StartingErrorInfo startingErrorInfo = new StartingErrorInfo();
                startingErrorInfo.Contract.Code = contractCode;
                startingErrorInfo.Contract.Folder = contractFolder;

                return startingErrorInfo;
            }
            catch
            {
                return null;
            }
            #endregion
        }

        /// <summary>
        /// エラー情報を保存する
        /// </summary>
        public void Save()
        {
            #region エラー情報を保存する
            try
            {
                // 情報ファイル
                string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

                XmlSerializer serializer = new XmlSerializer(typeof(StartingErrorInfo));
                // 書き込むファイルを開く（UTF-8 BOM無し）
                using (StreamWriter streamWriter = new StreamWriter(dataFile, false, new UTF8Encoding(false)))
                {
                    // シリアル化し、XMLファイルに保存する
                    serializer.Serialize(streamWriter, this);
                }
            }
            catch { }
            #endregion
        }

        /// <summary>
        /// エラー情報をロードする
        /// </summary>
        /// <param name="contractCode">契約コード</param>
        /// <param name="serverPath">サーバーの物理パス</param>
        /// <returns>タスク情報</returns>
        public static StartingErrorInfo Load(string contractCode, string serverPath)
        {
            #region エラー情報をロードする
            try
            {
                // 情報ファイル
                string dataFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), XML_FILE);

                if (!File.Exists(dataFile))
                {
                    // タスク実行前に、エラー情報がない場合
                    return null;
                }

                XmlSerializer serializer = new XmlSerializer(typeof(StartingErrorInfo));
                // 読み込むファイルを開く
                StartingErrorInfo startingErrorInfo = null;
                using (StreamReader streamReader = new StreamReader(dataFile, new UTF8Encoding(false)))
                {
                    // XMLファイルから読み込み、逆シリアル化する
                    startingErrorInfo = serializer.Deserialize(streamReader) as StartingErrorInfo;
                }

                return startingErrorInfo;
            }
            catch
            {
                // TODO
                return null;
            }
            #endregion
        }

        /// <summary>
        /// エラー情報をクリアする
        /// </summary>
        /// <param name="errorMsg">エラーメッセージ</param>
        public void Clear(ref string errorMsg)
        {
            #region エラー情報をクリアする
            // 情報ファイル
            string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

            if (!File.Exists(dataFile))
            {
                return;
            }

            try
            {
                File.Delete(dataFile);
            }
            catch (Exception ex)
            {
                errorMsg = ex.Message;
            }
            #endregion
        }
    }
}
