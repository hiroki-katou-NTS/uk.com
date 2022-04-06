using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Globalization;
using System.IO;
using System.Text;
using System.Web;

using WSISmile.Business.Common;
using WSISmile.Business.Link.Parameter.Accept;
using WSISmile.Business.Task;

namespace WSISmile.Business.Log
{
    /// <summary>
    /// ���O�t�@�C������֘A
    /// </summary>
    public class Logger
    {
        /// <summary>
        /// ���O�t�H���_
        /// </summary>
        public const string LOG_FOLDER = "Log";

        /// <summary>
        /// ���O���e����������
        /// </summary>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <param name="processName">�G���[�����������A�g��</param>
        /// <param name="TI">�^�X�N���</param>
        public static void WriteLog(string errorMsg, string processName, TaskInfo TI)
        {
            #region ���O���e���������� : WriteLog
            try
            {
                // ���O�t�@�C���̃p�X���擾
                DirectoryInfo logFolder = new DirectoryInfo(Path.Combine(TI.Contract.Folder, LOG_FOLDER));
                if (!logFolder.Exists)
                {
                    Directory.CreateDirectory(logFolder.FullName);
                }

                string logFilePath = Path.Combine(logFolder.FullName, "Smile" + DateTime.Now.ToString(Format.Date) + ".log");

                // ���O���e����������
                using (StreamWriter sw = new StreamWriter(logFilePath, true, Encode.Text))
                {
                    sw.WriteLine("[" + DateTime.Now.ToString(Format.DateAndTime_Log) + "]�@[" + processName + "]");
                    sw.WriteLine(errorMsg);
                }
            }
            catch (Exception ex)
            {
                string temp = ex.Message; // TODO
            }
            #endregion
        }

        /// <summary>
        /// �G���[���b�Z�[�W�̍ő�\������
        /// </summary>
        /// <param name="lstError">�G���[���b�Z�[�WList.</param>
        /// <returns>�G���[���b�Z�[�WList.���</returns>
        public static List<string> ErrorMsgLimit(List<string> lstError)
        {
            #region �G���[���b�Z�[�W�̍ő�\������ : ErrorMsgLimit
            int limit = int.Parse(ConfigurationManager.AppSettings["ErrorMsgLimit"]);
            if (lstError.Count > limit)
            {
                return lstError.GetRange(0, limit);
            }
            return lstError;
            #endregion
        }

        /// <summary>
        /// �ۊǊ��Ԋ����؂�̏��t�@�C�����폜
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        public static void RemoveExpiredTaskFiles(TaskInfo TI, ref string errorMsg)
        {
            #region �ۊǊ��Ԋ����؂�̏��t�@�C�����폜 : RemoveExpiredTaskFiles
            // �_��P�ʏ��t�H���_
            DirectoryInfo contractFolder = new DirectoryInfo(TI.Contract.Folder);

            try
            {
                foreach (FileInfo file in contractFolder.GetFiles())
                {
                    /*
                     * <<�폜�Ώۏ��t�@�C��>>
                     *  1) data.xml
                     *  2) �����Αӏ��
                     *  3) �e�������
                    */

                    // ���t�@�C���ۊǊ���(����)
                    int expiredDays = int.Parse(ConfigurationManager.AppSettings["ExpiredDays"]);

                    if (file.Name.Contains("_"))
                    {
                        string[] fileNames = file.Name.Split('_');
                        if (fileNames.Length > 1)
                        {
                            string dateFormatOrNot = fileNames[1].Replace(file.Extension, "");

                            DateTime fileDate = DateTime.Today;
                            if (DateTime.TryParseExact(dateFormatOrNot, Format.DateAndTime, DateTimeFormatInfo.InvariantInfo, DateTimeStyles.None, out fileDate))
                            {
                                TimeSpan span = DateTime.Now - fileDate;
                                if (span.Days >= expiredDays)
                                {
                                    file.Delete();
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                errorMsg = ex.Message;
            }
            #endregion
        }
    }
}
