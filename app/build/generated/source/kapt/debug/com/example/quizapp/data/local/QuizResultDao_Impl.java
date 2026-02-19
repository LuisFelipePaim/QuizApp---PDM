package com.example.quizapp.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.quizapp.data.model.QuizResult;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuizResultDao_Impl implements QuizResultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizResult> __insertionAdapterOfQuizResult;

  public QuizResultDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuizResult = new EntityInsertionAdapter<QuizResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quiz_results` (`id`,`userEmail`,`subject`,`score`,`totalQuestions`,`dateTimestamp`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuizResult entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserEmail() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserEmail());
        }
        if (entity.getSubject() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubject());
        }
        statement.bindLong(4, entity.getScore());
        statement.bindLong(5, entity.getTotalQuestions());
        statement.bindLong(6, entity.getDateTimestamp());
      }
    };
  }

  @Override
  public Object insertResult(final QuizResult result, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuizResult.insert(result);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<QuizResult>> getResultsByUser(final String email) {
    final String _sql = "SELECT * FROM quiz_results WHERE userEmail = ? ORDER BY dateTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quiz_results"}, new Callable<List<QuizResult>>() {
      @Override
      @NonNull
      public List<QuizResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfDateTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTimestamp");
          final List<QuizResult> _result = new ArrayList<QuizResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuizResult _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            final String _tmpSubject;
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _tmpSubject = null;
            } else {
              _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            }
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final long _tmpDateTimestamp;
            _tmpDateTimestamp = _cursor.getLong(_cursorIndexOfDateTimestamp);
            _item = new QuizResult(_tmpId,_tmpUserEmail,_tmpSubject,_tmpScore,_tmpTotalQuestions,_tmpDateTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SubjectStat>> getAverageScoreBySubject(final String email) {
    final String _sql = "SELECT subject, AVG(score) as averageScore FROM quiz_results WHERE userEmail = ? GROUP BY subject";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quiz_results"}, new Callable<List<SubjectStat>>() {
      @Override
      @NonNull
      public List<SubjectStat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSubject = 0;
          final int _cursorIndexOfAverageScore = 1;
          final List<SubjectStat> _result = new ArrayList<SubjectStat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectStat _item;
            final String _tmpSubject;
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _tmpSubject = null;
            } else {
              _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            }
            final double _tmpAverageScore;
            _tmpAverageScore = _cursor.getDouble(_cursorIndexOfAverageScore);
            _item = new SubjectStat(_tmpSubject,_tmpAverageScore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
