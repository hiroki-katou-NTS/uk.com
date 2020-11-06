<template>
<div class="cmms45shrcomponentsapp15">
    <!-- B1_4 -->
    <div class="field-set">
        <!-- B1_4_1 -->
        <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS20_23" | i18n }}</span>
          <span class="badge badge-warning"></span>
        </div>
      </div>
      <!-- B1_4_2 -->
      <div class="accordion">
        <div class="card">
          <div class="card-header">
            <button class="btn btn-link" type="button">
              <!-- tiêu đề của collapse -->
              <span>{{'this is card header' }}</span>
            </button>
          </div>
          <div class="collapse">
            <div class="card-body">
              <!-- nội dung của collapse -->
              {{ 'this is card body' }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- B1_5 -->
    <div class="field-set">
        <!-- B1_5_1 -->
        <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS20_24" | i18n }}</span>
          <span class="badge badge-warning"></span>
        </div>
      </div>
      <!-- B1_5_2 -->
      <div class="accordion">
        <div class="card">
          <div class="card-header">
            <button class="btn btn-link" type="button">
              <!-- tiêu đề của collapse -->
              <span>{{'this is card header 2' }}</span>
            </button>
          </div>
          <div class="collapse">
            <div class="card-body">
              <!-- nội dung của collapse -->
              {{ 'this is card body 2' }}
            </div>
          </div>
        </div>
      </div>
      <!-- B1_5_4 && B1_5_5 -->
      <label class="py-2 pl-4">{{'1,260円'}}</label>
    </div>
</div>
</template>