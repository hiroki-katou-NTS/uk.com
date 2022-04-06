using System;
using System.Collections.Generic;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Accept;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// �O������֘A���
    /// </summary>
    public class Accept
    {
        /// <summary>
        /// �O����������R�[�h (���sing.)
        /// </summary>
        public string ConditionSetCd = "";

        /// <summary>
        /// ����f�[�^�̍s���J�E���g(intMaxCnt�܂ŃJ�E���g���Ă���)
        /// </summary>
        public int RowCounter = 0;

        /// <summary>
        /// �O������O����.TaskID
        /// </summary>
        public string PrepareTaskId = "";

        /// <summary>
        /// �O��������s.TaskID
        /// </summary>
        public string ExecuteTaskId = "";

        /// <summary>
        /// �O������A�b�v���[�h.FileID
        /// </summary>
        public string FileId = "";

        /// <summary>
        /// �O�����File(Path)
        /// </summary>
        public string File = "";

        /// <summary>
        /// �O�����������
        /// </summary>
        public int TotalCount = 0;
    }
}
