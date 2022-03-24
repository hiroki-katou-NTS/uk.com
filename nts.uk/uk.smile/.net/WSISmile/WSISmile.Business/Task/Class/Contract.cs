using System;
using System.IO;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// �_����
    /// </summary>
    public class Contract
    {
        public Contract() { }

        /// <summary>
        /// �_��R�[�h
        /// </summary>
        public string Code = "";

        /// <summary>
        /// �_��p�X���[�h
        /// </summary>
        public string Password = "";

        /// <summary>
        /// �_��P�ʏ��t�H���_
        /// </summary>
        public string Folder = "";

        /// <summary>
        /// �_��t�H���_���쐬����
        /// </summary>
        /// <param name="contractCode">�_��R�[�h</param>
        /// <param name="serverPath">�T�[�o�[�̕����p�X</param>
        /// <returns>�_��t�H���_</returns>
        public static string CreateContractFolder(string contractCode, string serverPath)
        {
            #region �_��t�H���_���쐬����
            // Task�t�H���_(�_��P�ʂɊǗ�)
            string taskFolder = Path.Combine(serverPath, TaskInfo.TASK_FOLDER);

            // �_��t�H���_
            string contractFolder = Path.Combine(taskFolder, contractCode);

            if (!Directory.Exists(contractFolder))
            {
                // �_��t�H���_�����݂��Ȃ��ꍇ�ɁA�쐬����
                Directory.CreateDirectory(contractFolder);
            }

            return contractFolder;
            #endregion
        }
    }
}
