package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@ActivityScope
public class ScheduleDetailModel extends BaseModel implements ScheduleDetailContract.Model {

  @Inject
  public ScheduleDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<ScheduleDetail> getScheduleDetail(final String url) {
    return Flowable.create(new FlowableOnSubscribe<ScheduleDetail>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ScheduleDetail> e) throws Exception {
        Element html = Jsoup.connect(HtmlConstant.DILIDILI_URL + url).timeout(10000).get();
        if(html == null){
          e.onError(new Throwable("element html is null"));
        }else {
          ScheduleDetail scheduleDetail = JP.from(html, ScheduleDetail.class);
          e.onNext(scheduleDetail);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}