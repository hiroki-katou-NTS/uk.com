using System;
using System.IO;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// 契約情報
    /// </summary>
    public class Contract
    {
        public Contract() { }

        /// <summary>
        /// 契約コード
        /// </summary>
        public string Code = "";

        /// <summary>
        /// 契約パスワード
        /// </summary>
        public string Password = "";

        /// <summary>
        /// 契約単位情報フォルダ
        /// </summary>
        public string Folder = "";

        /// <summary>
        /// 契約フォルダを作成する
        /// </summary>
        /// <param name="contractCode">契約コード</param>
        /// <param name="serverPath">サーバーの物理パス</param>
        /// <returns>契約フォルダ</returns>
        public static string CreateContractFolder(string contractCode, string serverPath)
        {
            #region 契約フォルダを作成する
            // Taskフォルダ(契約単位に管理)
            string taskFolder = Path.Combine(serverPath, TaskInfo.TASK_FOLDER);

            // 契約フォルダ
            string contractFolder = Path.Combine(taskFolder, contractCode);

            if (!Directory.Exists(contractFolder))
            {
                // 契約フォルダが存在しない場合に、作成する
                Directory.CreateDirectory(contractFolder);
            }

            return contractFolder;
            #endregion
        }
    }
}
