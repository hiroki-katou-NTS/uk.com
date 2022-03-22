using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Text;
using System.Xml.Serialization;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// タスク情報
    /// </summary>
    public class TaskInfo
    {
        public TaskInfo() { }

        #region CONST
        /// <summary>
        /// タスクフォルダ
        /// </summary>
        public const string TASK_FOLDER = "Task";

        /// <summary>
        /// 情報ファイル
        /// </summary>
        private const string XML_FILE = "data.xml";
        #endregion CONST

        #region メンバー変数
        /// <summary>
        /// クッキー情報
        /// </summary>
        public Cookie Cookie = new Cookie();

        /// <summary>
        /// 契約情報
        /// </summary>
        public Contract Contract = new Contract();

        /// <summary>
        /// Smileの実行会社コード(給与支給会社コード) 4桁
        /// </summary>
        public string CompCode = "";

        /// <summary>
        /// Smileの実行会社CID(給与支給会社CID)
        /// 契約CD(12桁) + 会社CD(4桁)を連結したもの
        /// </summary>
        public string CompCid = "";

        /// <summary>
        /// Smileの実行者ID
        /// </summary>
        public string UserId = "";

        /// <summary>
        /// 実行処理種類
        /// </summary>
        public ProcessType ProcessType = ProcessType.Prepare;

        /// <summary>
        /// Smile連携設定情報
        /// </summary>
        public Setting Setting = new Setting();

        /// <summary>
        /// 外部受入関連情報
        /// </summary>
        public Accept Accept = new Accept();

        /// <summary>
        /// 外部出力関連情報
        /// </summary>
        public Output Output = new Output();

        /// <summary>
        /// エラーメッセージリスト
        /// </summary>
        public List<string> ErrorMsgList = new List<string>();
        #endregion メンバー変数

        /// <summary>
        /// タスク情報を生成する
        /// </summary>
        /// <param name="contractCode">契約コード</param>
        /// <param name="contractPass">契約パスワード</param>
        /// <param name="serverPath">サーバーの物理パス</param>
        /// <returns>タスク情報</returns>
        public static TaskInfo Create(string contractCode, string contractPass, string serverPath)
        {
            #region タスク情報を生成する
            try
            {
                // 契約フォルダ
                string contractFolder = Contract.CreateContractFolder(contractCode, serverPath);

                TaskInfo taskInfo = new TaskInfo();
                taskInfo.Contract.Code = contractCode;
                taskInfo.Contract.Password = contractPass;
                taskInfo.Contract.Folder = contractFolder;

                return taskInfo;
            }
            catch
            {
                return null;
            }
            #endregion
        }

        /// <summary>
        /// タスク情報を保存する
        /// </summary>
        public void Save()
        {
            #region タスク情報を保存する
            try
            {
                // 情報ファイル
                string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

                XmlSerializer serializer = new XmlSerializer(typeof(TaskInfo));
                // 書き込むファイルを開く（UTF-8 BOM無し）
                using (StreamWriter streamWriter = new StreamWriter(dataFile, false, new UTF8Encoding(false)))
                {
                    // シリアル化し、XMLファイルに保存する
                    serializer.Serialize(streamWriter, this);
                }
            }
            catch
            {
                // TODO
            }
            #endregion
        }

        /// <summary>
        /// タスク情報をロードする
        /// </summary>
        /// <param name="contractCode">契約コード</param>
        /// <param name="serverPath">サーバーの物理パス</param>
        /// <returns>タスク情報</returns>
        public static TaskInfo Load(string contractCode, string serverPath)
        {
            #region タスク情報をロードする
            try
            {
                // 情報ファイル
                string dataFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), XML_FILE);

                XmlSerializer serializer = new XmlSerializer(typeof(TaskInfo));
                // 読み込むファイルを開く
                TaskInfo taskInfo = null;
                using (StreamReader streamReader = new StreamReader(dataFile, new UTF8Encoding(false)))
                {
                    // XMLファイルから読み込み、逆シリアル化する
                    taskInfo = serializer.Deserialize(streamReader) as TaskInfo;
                }

                return taskInfo;
            }
            catch
            {
                // TODO
                return null;
            }
            #endregion
        }

        /// <summary>
        /// タスク情報をクリアする
        /// </summary>
        /// <param name="errorMsg">エラーメッセージ</param>
        public void Clear(ref string errorMsg)
        {
            #region タスク情報をクリアする
            // 情報ファイル
            string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

            if (!File.Exists(dataFile))
            {
                return;
            }

            // 調査の為、情報ファイルを別名にして保存
            string destFileName = Path.Combine(this.Contract.Folder, string.Format("data_{0}.xml", DateTime.Now.ToString(Format.DateAndTime)));

            try
            {
                File.Copy(dataFile, destFileName, true);

                File.Delete(dataFile);

                // 契約フォルダの中身を全消しが必要か?
            }
            catch (Exception ex)
            {
                errorMsg = ex.Message;
            }
            #endregion
        }

        /// <summary>
        /// 実行中であるかをチェック(排他制御)
        /// </summary>
        /// <param name="contractCode">契約CD</param>
        /// <param name="serverPath">HttpContext.Current.Server.MapPath</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <remarks>情報ファイル(data.xml)が存在する場合に、実行中である</remarks>
        /// <returns></returns>
        public static bool IsExecuting(string contractCode, string serverPath, ref string errorMsg)
        {
            #region 実行中であるかをチェック(排他制御)
            try
            {
                // 情報ファイル
                string dataFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), XML_FILE);

                // 契約フォルダにアクセス権限があるかをチェック兼ねて
                string testFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), string.Format("test_{0}.txt", DateTime.Now.ToString(Format.DateAndTime)));
                // ----------------------
                using (FileStream fs = File.Create(testFile))
                {
                    byte[] info = new UTF8Encoding(true).GetBytes("This is a test file.");
                    fs.Write(info, 0, info.Length);
                }
                File.Delete(testFile);

                if (File.Exists(dataFile))
                {
                    FileInfo dataFileInfo = new FileInfo(dataFile);
                    {
                        // dataファイルの最終更新日時
                        DateTime lastWrite = dataFileInfo.LastWriteTime;

                        // 定めた時間間隔(分単位)を超えた場合に、異常終了などと見なし強制的に処理を再開する
                        int resetMinutes = int.Parse(ConfigurationManager.AppSettings["ResetTime"]); // 初期値：45分

                        TimeSpan elapsedTime = DateTime.Now - lastWrite;
                        if (elapsedTime.TotalMinutes >= resetMinutes)
                        {
                            File.Delete(dataFile);
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }

                return false;
            }
            catch (Exception ex)
            {
                errorMsg = ex.Message;
                return false;
            }
            #endregion
        }

        /// <summary>
        /// 実行処理文言名称を取得する
        /// </summary>
        /// <returns></returns>
        public string GetProcessWording()
        {
            #region 実行処理文言名称を取得する
            return ProcessTypeEnumConverter.GetProcessWording(this.ProcessType);
            #endregion
        }
    }
}
