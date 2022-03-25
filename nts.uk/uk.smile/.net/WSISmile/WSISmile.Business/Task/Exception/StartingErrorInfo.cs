using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// �^�X�N���s�O* �G���[���
    /// </summary>
    public class StartingErrorInfo
    {
        public StartingErrorInfo() { }

        #region CONST
        /// <summary>
        /// ���t�@�C��
        /// </summary>
        private const string XML_FILE = "error.xml";
        #endregion CONST

        #region �����o�[�ϐ�
        /// <summary>
        /// �_����
        /// </summary>
        public Contract Contract = new Contract();

        /// <summary>
        /// �G���[���e
        /// </summary>
        public string ErrorMsg = string.Empty;
        #endregion �����o�[�ϐ�

        /// <summary>
        /// �G���[���𐶐�����
        /// </summary>
        /// <param name="contractCode">�_��R�[�h</param>
        /// <param name="serverPath">�T�[�o�[�̕����p�X</param>
        /// <returns>�^�X�N���</returns>
        public static StartingErrorInfo Create(string contractCode, string serverPath)
        {
            #region �G���[���𐶐�����
            try
            {
                // �_��t�H���_
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
        /// �G���[����ۑ�����
        /// </summary>
        public void Save()
        {
            #region �G���[����ۑ�����
            try
            {
                // ���t�@�C��
                string dataFile = Path.Combine(this.Contract.Folder, XML_FILE);

                XmlSerializer serializer = new XmlSerializer(typeof(StartingErrorInfo));
                // �������ރt�@�C�����J���iUTF-8 BOM�����j
                using (StreamWriter streamWriter = new StreamWriter(dataFile, false, new UTF8Encoding(false)))
                {
                    // �V���A�������AXML�t�@�C���ɕۑ�����
                    serializer.Serialize(streamWriter, this);
                }
            }
            catch { }
            #endregion
        }

        /// <summary>
        /// �G���[�������[�h����
        /// </summary>
        /// <param name="contractCode">�_��R�[�h</param>
        /// <param name="serverPath">�T�[�o�[�̕����p�X</param>
        /// <returns>�^�X�N���</returns>
        public static StartingErrorInfo Load(string contractCode, string serverPath)
        {
            #region �G���[�������[�h����
            try
            {
                // ���t�@�C��
                string dataFile = Path.Combine(Contract.CreateContractFolder(contractCode, serverPath), XML_FILE);

                if (!File.Exists(dataFile))
                {
                    // �^�X�N���s�O�ɁA�G���[��񂪂Ȃ��ꍇ
                    return null;
                }

                XmlSerializer serializer = new XmlSerializer(typeof(StartingErrorInfo));
                // �ǂݍ��ރt�@�C�����J��
                StartingErrorInfo startingErrorInfo = null;
                using (StreamReader streamReader = new StreamReader(dataFile, new UTF8Encoding(false)))
                {
                    // XML�t�@�C������ǂݍ��݁A�t�V���A��������
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
        /// �G���[�����N���A����
        /// </summary>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        public void Clear(ref string errorMsg)
        {
            #region �G���[�����N���A����
            // ���t�@�C��
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
