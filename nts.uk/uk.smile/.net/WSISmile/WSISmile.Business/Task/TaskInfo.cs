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
    /// �^�X�N���
    /// </summary>
    public class TaskInfo
    {
        public TaskInfo() { }

        #region CONST
        /// <summary>
        /// �^�X�N�t�H���_
        /// </summary>
        public const string TASK_FOLDER = "Task";

        /// <summary>
        /// ���t�@�C��
        /// </summary>
        private const string XML_FILE = "data.xml";
        #endregion CONST

        #region �����o�[�ϐ�
        /// <summary>
        /// �N�b�L�[���
        /// </summary>
        public Cookie Cookie = new Cookie();

        /// <summary>
        /// �_����
        /// </summary>
        public Contract Contract = new Contract();

        /// <summary>
        /// Smile�̎��s��ЃR�[�h 4��
        /// </summary>
        public string CompCode = "";

        /// <summary>
        /// Smile�̎��s��ID
        /// </summary>
        public string UserId = "";

        /// <summary>
        /// ���s�������
        /// </summary>
        public ProcessType ProcessType = ProcessType.Prepare;

        /// <summary>
        /// Smile�A�g�ݒ���
        /// </summary>
        public Setting Setting = new Setting();

        /// <summary>
        /// �O������֘A���
        /// </summary>
        public Accept Accept = new Accept();

        /// <summary>
        /// �O���o�͊֘A���
        /// </summary>
        public Output Output = new Output();

        /// <summary>
        /// �G���[���b�Z�[�W���X�g
        /// </summary>
        public List<string> ErrorMsgList = new List<string>();
        #endregion �����o�[�ϐ�

        /// <summary>
        /// �^�X�N���𐶐�����
        /// </summary>
        /// <param name="contractCode">�_��R�[�h</param>
        /// <param name="contractPass">�_��p�X���[�h</param>
        /// <param name="serverPath">�T�[�o�[�̕����p�X</param>
        /// <returns>�^�X�N���</returns>
        public static TaskInfo Create(string contractCode, string contractPass, string serverPath)
        {
            #region �^�X�N���𐶐�����
            try
            {
                // �_��t�H���_
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
        /// �^�X�N����ۑ�����
        /// </summary>
        public void Save()
        {
            #region �^�X�N����ۑ�����
            try
            {
                // ���t�@�C��
                string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

                XmlSerializer serializer = new XmlSerializer(typeof(TaskInfo));
                // �������ރt�@�C�����J���iUTF-8 BOM�����j
                using (StreamWriter streamWriter = new StreamWriter(dataFile, false, new UTF8Encoding(false)))
                {
                    // �V���A�������AXML�t�@�C���ɕۑ�����
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
        /// �^�X�N�������[�h����
        /// </summary>
        /// <param name="contractCode">�_��R�[�h</param>
        /// <param name="serverPath">�T�[�o�[�̕����p�X</param>
        /// <returns>�^�X�N���</returns>
        public static TaskInfo Load(string contractCode, string serverPath)
        {
            #region �^�X�N�������[�h����
            try
            {
                // ���t�@�C��
                string dataFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), XML_FILE);

                XmlSerializer serializer = new XmlSerializer(typeof(TaskInfo));
                // �ǂݍ��ރt�@�C�����J��
                TaskInfo taskInfo = null;
                using (StreamReader streamReader = new StreamReader(dataFile, new UTF8Encoding(false)))
                {
                    // XML�t�@�C������ǂݍ��݁A�t�V���A��������
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
        /// �^�X�N�����N���A����
        /// </summary>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        public void Clear(ref string errorMsg)
        {
            #region �^�X�N�����N���A����
            // ���t�@�C��
            string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

            if (!File.Exists(dataFile))
            {
                return;
            }

            // �����ׁ̈A���t�@�C����ʖ��ɂ��ĕۑ�
            string destFileName = Path.Combine(this.Contract.Folder, string.Format("data_{0}.xml", DateTime.Now.ToString(Format.DateAndTime)));

            try
            {
                File.Copy(dataFile, destFileName, true);

                File.Delete(dataFile);

                // �_��t�H���_�̒��g��S�������K�v��?
            }
            catch (Exception ex)
            {
                errorMsg = ex.Message;
            }
            #endregion
        }

        /// <summary>
        /// ���s���ł��邩���`�F�b�N(�r������)
        /// </summary>
        /// <param name="contractCode">�_��CD</param>
        /// <param name="serverPath">HttpContext.Current.Server.MapPath</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <remarks>���t�@�C��(data.xml)�����݂���ꍇ�ɁA���s���ł���</remarks>
        /// <returns></returns>
        public static bool IsExecuting(string contractCode, string serverPath, ref string errorMsg)
        {
            #region ���s���ł��邩���`�F�b�N(�r������)
            try
            {
                // ���t�@�C��
                string dataFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), XML_FILE);

                // �_��t�H���_�ɃA�N�Z�X���������邩���`�F�b�N���˂�
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
                        // data�t�@�C���̍ŏI�X�V����
                        DateTime lastWrite = dataFileInfo.LastWriteTime;

                        // ��߂����ԊԊu(���P��)�𒴂����ꍇ�ɁA�ُ�I���Ȃǂƌ��Ȃ������I�ɏ������ĊJ����
                        int resetMinutes = int.Parse(ConfigurationManager.AppSettings["ResetTime"]); // �����l�F45��

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
        /// ���s�����������̂��擾����
        /// </summary>
        /// <returns></returns>
        public string GetProcessWording()
        {
            #region ���s�����������̂��擾����
            return ProcessTypeEnumConverter.GetProcessWording(this.ProcessType);
            #endregion
        }
    }
}
