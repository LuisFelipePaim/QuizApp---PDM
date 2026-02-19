package com.example.quizapp.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.quizapp.data.model.Question;
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

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  private final Converters __converters = new Converters();

  public QuestionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `questions` (`id`,`text`,`options`,`correctOptionIndex`,`difficulty`,`subject`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Question entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getText() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getText());
        }
        final String _tmp = __converters.fromList(entity.getOptions());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getCorrectOptionIndex());
        if (entity.getDifficulty() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDifficulty());
        }
        if (entity.getSubject() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSubject());
        }
      }
    };
  }

  @Override
  public Object insertQuestions(final List<Question> questions,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuestion.insert(questions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object getQuestionsBySubject(final String subject,
      final Continuation<? super List<Question>> arg1) {
    final String _sql = "SELECT * FROM questions WHERE subject = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Question>>() {
      @Override
      @NonNull
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectOptionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "correctOptionIndex");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Question _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final List<String> _tmpOptions;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfOptions)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfOptions);
            }
            _tmpOptions = __converters.toList(_tmp);
            final int _tmpCorrectOptionIndex;
            _tmpCorrectOptionIndex = _cursor.getInt(_cursorIndexOfCorrectOptionIndex);
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpSubject;
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _tmpSubject = null;
            } else {
              _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            }
            _item = new Question(_tmpId,_tmpText,_tmpOptions,_tmpCorrectOptionIndex,_tmpDifficulty,_tmpSubject);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object getAllQuestions(final Continuation<? super List<Question>> arg0) {
    final String _sql = "SELECT * FROM questions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Question>>() {
      @Override
      @NonNull
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectOptionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "correctOptionIndex");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Question _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final List<String> _tmpOptions;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfOptions)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfOptions);
            }
            _tmpOptions = __converters.toList(_tmp);
            final int _tmpCorrectOptionIndex;
            _tmpCorrectOptionIndex = _cursor.getInt(_cursorIndexOfCorrectOptionIndex);
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpSubject;
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _tmpSubject = null;
            } else {
              _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            }
            _item = new Question(_tmpId,_tmpText,_tmpOptions,_tmpCorrectOptionIndex,_tmpDifficulty,_tmpSubject);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
