<template>
<div class="kdws03g p-0">
  <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0">
      <!-- G1_1: 戻るボタン -->
      <div class="col-1 pl-1" v-on:click="$close()"><i class="fas fa-arrow-left"></i></div>
      <!-- G1_2: 対象社員名 -->
      <div class="col-11 pl-0 pr-2"><span>{{ empName }}</span></div>
    </div>
  </div>
    <div class="accordion accordion-mn3">
      <!-- G2_1: 休暇残数 -->
      <div class="card  show border border-left-0 border-right-0" v-if="!params.remainOrtime36">
        <div class="card-header uk-bg-accordion">
           <button class="btn btn-link" type="button">{{'KDWS03_70' | i18n}}</button>
        </div>
        <div class="collapse">
          <div class="card-body">
              <div class="row" v-if="remainNumber.manageYear">
                <!-- G3_1: 年休残 -->
                <div class="col-9">{{'KDWS03_47' | i18n}}</div>
                <!-- G3_2: 年休残数 -->
                <div class="col-3 text-right">{{'KDWS03_52' | i18n(remainNumber.yearRemain)}}</div>
              </div>
              <div class="row" v-if="remainNumber.manageYear" v-bind:class="{'pt-3': remainNumber.manageYear}">
                <!-- G4_1: 次回付与 -->
                <div class="col-7">{{'KDWS03_51' | i18n}}</div>
                <!-- G4_2: 次回付与日 -->
                <div class="col-5 text-right">{{remainNumber.nextGrantDate}}</div>
              </div>
              <div class="row" v-if="remainNumber.manageReserve && remainNumber.manageYear" v-bind:class="{'pt-3': remainNumber.manageYear}">
                <!-- G5_1: 積立年休残 -->
                <div class="col-9">{{'KDWS03_48' | i18n}}</div>
                <!-- G5_2: 積立年休残数 -->
                <div class="col-3 text-right">{{'KDWS03_52' | i18n(remainNumber.reserveRemain)}}</div>
              </div>
              <div class="row" v-if="remainNumber.manageCompensatory" v-bind:class="{'pt-3': remainNumber.manageReserve || remainNumber.manageYear}">
                <!-- G6_1: 代休残 -->
                <div class="col-9">{{'KDWS03_49' | i18n}}</div>
                <!-- G6_2: 代休残数 -->
                <div class="col-3 text-right">{{'KDWS03_52' | i18n(remainNumber.compensatoryRemain)}}</div>
              </div>
              <div class="row" v-if="remainNumber.manageSubStitute" 
                v-bind:class="{'pt-3': remainNumber.manageCompensatory || remainNumber.manageReserve || remainNumber.manageYear}">
                <!-- G7_1: 振休残 -->
                <div class="col-9">{{'KDWS03_50' | i18n}}</div>
                <!-- G7_2: 振休残数 -->
                <div class="col-3 text-right">{{'KDWS03_52' | i18n(remainNumber.substituteRemain)}}</div>
              </div>
          </div>
        </div>
      </div>
      <!-- G2_2: 時間外超過時間 -->
      <div class="card show border border-left-0 border-right-0" v-if="time36.showAgreement && params.remainOrtime36">
        <div class="card-header uk-bg-accordion">
          <button class="btn btn-link" type="button">{{'KDWS03_53' | i18n}}</button>
        </div>
        <div class="collapse">
          <div class="card-body">
                <div class="row">
                <!-- G8_1: 超過時間 -->
                <div class="col-9">{{'KDWS03_54' | i18n}}</div>
                <!-- G8_2: 超過時間 -->
                <div class="col-3 text-right">{{time36.time36 | timedr}}</div>
              </div>
              <div class="row pt-3">
                <!-- G9_1: 超過上限時間 -->
                <div class="col-9">{{'KDWS03_55' | i18n}}</div>
                <!-- G9_2: 超過上限時間 -->
                <div class="col-3 text-right">{{time36.maxTime36 | timedr}}</div>
              </div>
              <div class="row pt-3">
                <!-- G10_1: 超過回数 -->
                <div class="col-9">{{'KDWS03_56' | i18n}}</div>
                <!-- G10_2: 超過回数 -->
                <div class="col-3 text-right">{{'KDWS03_58' | i18n(time36.excessNumber)}}</div>
              </div>
              <div class="row pt-3">
                <!-- G11_1: 超過上限回数 -->
                <div class="col-9">{{'KDWS03_57' | i18n}}</div>
                <!-- G11_2: 超過上限回数 -->
                <div class="col-3 text-right">{{'KDWS03_58' | i18n(time36.maxExcessNumber)}}</div>
              </div>
          </div>
        </div>
      </div>
    </div>
</div>
</template>