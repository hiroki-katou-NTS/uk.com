<template>
  <div class="kafs05step2">
    <!-- top-->
    <div>
      <kafs00-a
        v-if="$appContext.kaf000_A_Params != null"
        v-bind:params="$appContext.kaf000_A_Params"
      />
    </div>
    <div
      v-if="!$appContext.$valid || !$appContext.isValidateAll"
      class="card bg-danger top-alert uk-text-danger topError"
    >
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS07_1" | i18n }}
      </button>
    </div>

    <!-- OverTime -->
    <div v-if="true" class="card card-label">
      <div class="card-header uk-bg-accordion mt-2 mb-n2">
        <span>{{ "KAFS05_70" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div v-for="(item, index) in overTimes" v-bind:key="index" :value="index">
        <div class="card-body">
          <div class="row mt-3">
            <div class="col-3">{{ item.title }}</div>
            <div class="col-5">
              <kafs00subp1 v-bind:params="item.preApp" />
            </div>
            <div class="col-3.5">
              <kafs00subp1 v-bind:params="item.actualApp" />
            </div>
          </div>
          <div class="card-body">
            <nts-time-editor
              v-model="item.applicationTime"
              name=""
              require="true"
              showTitle="true"
              time-input-type="time-duration"
            />
          </div>
        </div>
      </div>
    </div>

    <!-- HolidayTime -->

    <div v-if="true" class="card card-label">
      <div class="card-header uk-bg-accordion mt-2 mb-n2">
        <span>{{ "KAFS05_73" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div
        v-for="(item, index) in holidayTimes"
        v-bind:key="index"
        :value="index"
      >
        <div class="card-body">
          <div class="row mt-3">
            <div class="col-3">{{ item.title }}</div>
            <div class="col-5">
              <kafs00subp1 v-bind:params="item.preApp" />
            </div>
            <div class="col-3.5">
              <kafs00subp1 v-bind:params="item.actualApp" />
            </div>
          </div>
          <div class="card-body">
            <nts-time-editor
              v-model="item.applicationTime"
              name=""
              require="true"
              showTitle="true"
              time-input-type="time-duration"
            />
          </div>
        </div>
      </div>
    </div>

    <div>
      <kafs00-c
        v-if="$appContext.kaf000_C_Params != null"
        v-bind:params="$appContext.kaf000_C_Params"
        v-on:kaf000CChangeReasonCD="kaf000CChangeReasonCD"
        v-on:kaf000CChangeAppReason="kaf000CChangeAppReason"
      />
    </div>

    <!--A2_B5-->

    <div class="card card-label" v-if="true">
        <!--A2_B5_1-->
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n1">
                {{'KAFS05_78' | i18n}}</v-label>
            <span class="badge badge-info">任意</span>
        </div>
        <div class="card-body">
            <!--A2_B5_2-->
            <div>
                  <div class="mb-1">
                        <span class="small-header">{{'KAFS05_79' | i18n}}</span>
                  </div>
                  <div>
                        <nts-dropdown v-model="selectedValue">
                            <option v-for="(item, index) in dropdownList" :key="index" :value="item.code">
                                {{item.code}} &nbsp;&nbsp;&nbsp;  {{item.text}}
                            </option>
                        </nts-dropdown>
                  </div>
            </div>
            <!--A2_B5_1-->
            <div>
                  <div class="mb-1">
                        <span class="small-header">{{'KAFS05_80' | i18n}}</span>
                  </div>
                  <div>
                        <nts-text-area　v-model="reason1" />
                  </div>
            </div>
        </div>
    </div>



    <div class="card card-label" v-if="true">
        <!--A2_B5_1-->
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n1">
                {{'KAFS05_78' | i18n}}</v-label>
            <span class="badge badge-info">任意</span>
        </div>
        <div class="card-body">
            <!--A2_B5_2-->
            <div>
                  <div class="mb-1">
                        <span class="small-header">{{'KAFS05_79' | i18n}}</span>
                  </div>
                  <div>
                        <nts-dropdown v-model="selectedValue">
                            <option v-for="(item, index) in dropdownList" :key="index" :value="item.code">
                                {{item.code}} &nbsp;&nbsp;&nbsp;  {{item.text}}
                            </option>
                        </nts-dropdown>
                  </div>
            </div>
            <!--A2_B5_1-->
            <div>
                  <div class="mb-1">
                        <span class="small-header">{{'KAFS05_80' | i18n}}</span>
                  </div>
                  <div>
                        <nts-text-area　v-model="reason1" />
                  </div>
            </div>
        </div>
    </div>

    <!--A2_B6-->

    <div class="card card-label">
      <!-- A2_C1_1 -->
      <button
        type="button"
        class="btn btn-block btn-primary btn-lg"
        v-on:click="$appContext.register"
        v-if="true"
      >
        {{ nameInsert }}
      </button>
      <!-- A2_C1_2 -->
      <button
        type="button"
        class="btn btn-block btn-secondary"
        v-if="true"
        v-on:click="backStep1"
      >
        {{ "KAFS05_12" | i18n }}
      </button>
    </div>
  </div>
</template>