using System;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// �ٗp�^���߁^�����N���^���ߊ��ԏ��(�x�����敪����)
    /// </summary>
    public class MonthlyClosingEmployInfo
    {
        #region �����ϐ�
        private string _employmentCd = "";

        private int _closureId = 0;

        private int _processMonth = 0;

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �ٗp�R�[�h
        /// </summary>
        public string EmploymentCd
        {
            get { return _employmentCd; }
            set { _employmentCd = value; }
        }

        /// <summary>
        /// ����ID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }

        /// <summary>
        /// ����(�Ώ�)�N��(���ߌ��̓��� or �O��) (yyyyMM)
        /// </summary>
        public int ProcessMonth
        {
            get { return _processMonth; }
            set { _processMonth = value; }
        }

        /// <summary>
        /// ���ߊJ�n��
        /// </summary>
        public DateTime StartDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// ���ߏI����
        /// </summary>
        public DateTime EndDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }
        #endregion �v���p�e�B
    }
}
