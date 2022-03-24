using System;
using System.Collections.Generic;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Output;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// �O���o�͊֘A���
    /// </summary>
    public class Output
    {
        public Output() { }

        /// <summary>
        /// �O���o�͂̐ݒ���
        /// </summary>
        public OutputSettingInfo Setting = new OutputSettingInfo();

        /// <summary>
        /// �O���o��TaskID
        /// </summary>
        public string TaskId = "";

        /// <summary>
        /// �O���o��FileID
        /// </summary>
        public string FileId = "";

        /// <summary>
        /// �O���o��File(Path)
        /// </summary>
        public string File = "";

        /// <summary>
        /// �x�����敪
        /// </summary>
        public int Payment = 0;

        /// <summary>
        /// SMILE�̏����N
        /// </summary>
        public int SmileYear = 0;

        /// <summary>
        /// SMILE�̏�����
        /// </summary>
        public int SmileMonth = 0;

        /// <summary>
        /// �x�����敪�ɊY������ٗp�^���߁^�����N���^���ߊ��ԏ�񃊃X�g
        /// </summary>
        public List<MonthlyClosingEmployInfo> MonthlyClosingEmployInfoList = new List<MonthlyClosingEmployInfo>();
    }
}
