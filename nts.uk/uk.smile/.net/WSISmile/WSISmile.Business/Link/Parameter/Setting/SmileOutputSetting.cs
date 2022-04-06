using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Setting
{
    /// <summary>
    /// Smile�A�g-�O���o�͐ݒ���
    /// </summary>
    public class SmileOutputSetting
    {
        #region Smile�A�g-�O���o�͐ݒ���
        #region �R���X�g���N�^
        public SmileOutputSetting() { }

        public SmileOutputSetting(JObject aJObject)
        {
            if (aJObject["salaryCooperationClassification"] != null)
            {
                this._useDefaultSetCd = Convert.ToBoolean(aJObject.GetValue("salaryCooperationClassification").ToObject<int>());
            }

            if (aJObject["salaryCooperationConditions"] != null)
            {
                this._conditionSetCd = aJObject.GetValue("salaryCooperationConditions").ToObject<string>();
            }

            if (aJObject["monthlyLockClassification"] != null)
            {
                this._checkLockStatus = Convert.ToBoolean(aJObject.GetValue("monthlyLockClassification").ToObject<int>());
            }

            if (aJObject["monthlyApprovalCategory"] != null)
            {
                this._checkApproveStatus = Convert.ToBoolean(aJObject.GetValue("monthlyApprovalCategory").ToObject<int>());
            }

            if (aJObject["linkedPaymentConversion"] != null)
            {
                foreach (JObject payment in aJObject.GetValue("linkedPaymentConversion").ToObject<JArray>())
                {
                    PaymentInfo paymentInfo = new PaymentInfo(payment);
                    _paymentInfoList.Add(paymentInfo);
                }
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private bool _useDefaultSetCd = false;

        private string _conditionSetCd = string.Empty;

        private bool _checkLockStatus = false;

        private bool _checkApproveStatus = false;

        private List<PaymentInfo> _paymentInfoList = new List<PaymentInfo>();
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �ϓ����ڋ敪(�f�t�H���g�̊O���o�͏����R�[�h���g����)
        /// </summary>
        public bool UseDefaultSetCd
        {
            get { return _useDefaultSetCd; }
            set { _useDefaultSetCd = value; }
        }

        /// <summary>
        /// �O���o�͏����R�[�h
        /// </summary>
        public string ConditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }

        /// <summary>
        /// �w���ʎ��яC���̃��b�N�x�`�F�b�N�敪
        /// </summary>
        public bool CheckLockStatus
        {
            get { return _checkLockStatus; }
            set { _checkLockStatus = value; }
        }

        /// <summary>
        /// �w���ʎ��т̊m�F�x�`�F�b�N�敪
        /// </summary>
        public bool CheckApproveStatus
        {
            get { return _checkApproveStatus; }
            set { _checkApproveStatus = value; }
        }

        /// <summary>
        /// �x�����敪���List.
        /// </summary>
        public List<PaymentInfo> PaymentInfoList
        {
            get { return _paymentInfoList; }
            set { _paymentInfoList = value; }
        }
        #endregion
        #endregion
    }

    /// <summary>
    /// �x�����敪���
    /// </summary>
    public class PaymentInfo
    {
        #region �x�����敪���
        #region �R���X�g���N�^
        public PaymentInfo() { }

        public PaymentInfo(JObject aJObject)
        {
            this._payment = aJObject.GetValue("paymentCode").ToObject<int>();

            foreach (JObject employTargetMonth in aJObject.GetValue("selectiveEmploymentCodes").ToObject<JArray>())
            {
                PayEmployTargetMonthInfo payEmployTargetMonthInfo = new PayEmployTargetMonthInfo(employTargetMonth);
                _payEmpMonthList.Add(payEmployTargetMonthInfo);
            }
        }
        #endregion

        #region �����ϐ�
        private int _payment = 0;

        private List<PayEmployTargetMonthInfo> _payEmpMonthList = new List<PayEmployTargetMonthInfo>();
        #endregion

        #region �v���p�e�B
        /// <summary>
        /// �x�����敪
        /// </summary>
        public int Payment
        {
            get { return _payment; }
            set { _payment = value; }
        }

        /// <summary>
        /// �x�����敪���Ƃ̌ٗp�^�Ώی�(�A�����̐ݒ�)���List.
        /// </summary>
        public List<PayEmployTargetMonthInfo> PayEmployTargetMonthInfoList
        {
            get { return _payEmpMonthList; }
            set { _payEmpMonthList = value; }
        }
        #endregion
        #endregion
    }

    /// <summary>
    /// �x�����敪���Ƃ̌ٗp�^�Ώی�(�A�����̐ݒ�)���
    /// </summary>
    public class PayEmployTargetMonthInfo
    {
        #region �x�����敪���Ƃ̌ٗp�^�Ώی�(�A�����̐ݒ�)���
        #region �R���X�g���N�^
        public PayEmployTargetMonthInfo() { }

        public PayEmployTargetMonthInfo(JObject aJObject)
        {
            this._employmentCd = aJObject.GetValue("scd").ToObject<string>();

            this._targetMonth = (TargetMonth)System.Enum.ToObject(typeof(TargetMonth), aJObject.GetValue("interlockingMonthAdjustment").ToObject<int>());
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private string _employmentCd = "";

        private TargetMonth _targetMonth = TargetMonth.Current;
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
        /// �Ώی�(�A�����̐ݒ�)
        /// </summary>
        public TargetMonth TargetMonth
        {
            get { return _targetMonth; }
            set { _targetMonth = value; }
        }
        #endregion �v���p�e�B
        #endregion
    }
}
