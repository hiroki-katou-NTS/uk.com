<template>
  <div class="kafs08a2 mt-4 mx-n3">
    <!-- A2_1 -->
    <div class="card bg-danger top-alert uk-text-danger topError">
              <button class="btn btn-link uk-text-danger">
                <i class="fa fa-exclamation-circle" aria-hidden="true" ></i>
                {{ 'KAFS08_13' | i18n }}
              </button>
    </div>
    <!-- A7 -->
    <div class="field-set mt-3 ml-3">
      <!-- A7_1 -->
      <label class="uk-text-dark-gray">{{'KAFS08_20' | i18n}}</label>
      <!-- A7_2 -->
      <div class="a7_2">
        <label>事前事後</label>
      </div>
      <!-- A7_3 -->
      <label class="uk-text-dark-gray">{{'KAFS08_23' | i18n}}</label>
      <!-- A7_4 -->
      <div class="a7_4">
        <label>申請日付</label>
      </div>
      <!-- A7_5 -->
      <div class="uk-text-dark-gray">
        <label class="a7_5">{{'KAFS08_28' | i18n}}</label>
        <!-- A7_6 -->
        <label>{{(derpartureTime || 0) | timewd}}</label>
      </div>
      <!-- A7_7 -->
      <div class="uk-text-dark-gray">
        <label class="a7_7">{{'KAFS08_29' | i18n}}</label>
        <!-- A7_8 -->
        <label>{{(returnTime || 0) | timewd}}</label>
      </div>
      <!-- A6 -->
      <div class="field-set">
        <!-- A6_1 -->
        <span class="a6_1">コメント１</span>
      </div>
      <!-- A_10 -->
      <div class="mx-n2">
        <table class="table uk-table-striped table-bordered">
          <thead>
            <tr>
              <th class="px-1" scope="col">{{"KAFS08_34" | i18n}}</th>
              <th class="px-1" scope="col">{{"KAFS08_35" | i18n}}</th>
              <th class="px-1" scope="col">{{"KAFS08_36" | i18n}}</th>
              <th class="px-1" scope="col">{{"KAFS08_37" | i18n}}</th>
              <th class="px-1 border-right-0" scope="col">{{"KAFS08_38" | i18n}}</th>
              <th class="border-left-0"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(data,index) in mtable" v-bind:key="index">
              <td class="px-1">{{data.date | date('MM/DD(ddd)')}}</td>
              <td class="px-1">{{data.wkTypeCd}}</td>
              <td class="px-1">{{data.wkTimeCd}}</td>
              <td class="px-1">{{data.wkTimeCd | timewd}}</td>
              <td class="border-right-0 px-1">{{data.endWorkTime | timewd}}</td>
              <td class="text-center px-0 border-left-0">
                <fa-font class="pr-4" v-bind:size="'1'" icon="angle-right" v-click="showModal.bind(this, 'modal')" />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- A50_F2 -->
      <div class="process-button">
        <!-- A50_2 -->
        <button @click="nextToStepThree()" class="btn btn-primary btn-block">{{'申請する' | i18n}}</button>
        <!-- A50_3 -->
        <button @click="prevStepOne()" class="btn btn-secondary btn-block">{{'KAFS08_40' | i18n}}</button>
      </div>
    </div>
  </div>
</template>