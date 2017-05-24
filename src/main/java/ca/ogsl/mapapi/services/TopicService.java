package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.dao.GenericDao;
import ca.ogsl.mapapi.dao.TopicDao;
import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.Topic;
import ca.ogsl.mapapi.util.ValidationUtil;

import java.util.List;

public class TopicService {

    private GenericDao genericDao = new GenericDao();
    private TopicDao topicDao = new TopicDao();

    public List<Topic> listTopics(String lang) throws AppException {
        return this.genericDao.getAllEntities(lang, Topic.class);
    }

    public Topic getTopicForId(String lang, Integer id) {
        return this.genericDao.getEntityFromId(lang, id, Topic.class);
    }

    public Topic getTopicForCode(String lang, String code) throws Exception {
        return this.topicDao.getTopicForCode(lang, code);
    }

    public Topic postCreateTopic(Topic topic, String role) throws AppException {
        ValidationUtil.validateAdminRole(role);
        return this.genericDao.mergeEntity(topic);
    }

    public List<Layer> getBaseLayersForTopic(String lang, String code) {
        return this.topicDao.getBaseLayersForTopicCode(lang, code);
    }

    public List<Layer> getLayersForTopic(String lang, String code) {
        return this.topicDao.getLayersForTopicCode(lang, code);
    }
}
